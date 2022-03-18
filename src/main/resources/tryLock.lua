--- 1 success, -1 failed

--- get lock key
local key = KEYS[1]
local requestId = ARGV[1]
local ttl = tonumber(ARGV[2])

local result = redis.call('setnx', key, requestId)
if result == 1 then
    redis.call('pexpire', key, ttl)
else
    result = -1;
    --- 如果　Value 相同, 則認為是相同執行緒, 則認為重入鎖
    local value = redis.call('get', key)
    if (value == requestId) then
        result = 1;
        redis.call('pexpire', key, ttl)
    end
end
return result