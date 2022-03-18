package com.occultus.learncase.idempotent;

import com.occultus.learncase.idempotent.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/idempotent")
public class idempotentController {
    @Autowired
    private TestService testService;

    /**
     * 1. 樂觀鎖 - 針對 狀態更新/版本號/Timestamp 條件...等等
     * 2. 悲觀鎖 - Select For Update DB鎖機制達到幕等
     * 3. 悲觀鎖 - DataBase DB PK, index 設置 (僅限新增)
     * 4. 樂觀鎖 - Redis 分散式鎖(使用 .setIfAbsent() 相當於 setnx 命令) v1,v2,v3
     * */
    @GetMapping("/sqlLock")
    public void sqlLock() {
        testService.selectForUpdate();
    }

    @GetMapping("/redisLock")
    public void redisLock() {
        /** v1. 透過 redis 計算數量 */
//         testService.redisDistributedDeductCountV1();

        /** v2. 因為 redis 是單執行緒模型, 透過設 "一組值" 來當作入口判斷, 如果尚未刪除或超時,則不給進 */
        /** redisTemplate 版本 */
//        testService.redisDistributedDeductCountV2_RedisTemplate();
        /** LuaScript 版本, 含自旋, 重入鎖 */
        testService.redisDistributedDeductCountV2_LuaScript();

        /** v3. 加鎖/解鎖 歸一化, 考量到另一條執行緒 "誤解鎖頭" 正在執行的執行緒
         *  - 重入鎖設計 - 考慮到可能多次呼叫上鎖機制(重入), 都沒有解鎖
         *  - 自旋鎖設計 - 考慮到不能一次拿到鎖,就直接返回錯誤不友善
         *  - 時間延長時間設計 - 考慮到"偶發"超時情境, 延長 lock 超時限制時間
         * */
//        testService.redisDistributedDeductCountV3();
    }
}
