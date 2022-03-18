package com.occultus.learncase.redis;

import com.occultus.learncase.idempotent.entity.Test;
import com.occultus.learncase.idempotent.repo.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private TestRepository testRepository;

    @PostMapping("/test")
    @Cacheable(value = "test", key = "#id")
    public String getTest(@RequestParam("id") Integer id) {
        Test test = testRepository.getById(id);
        log.warn("{}", test);
        return test.getName();
    }
}
