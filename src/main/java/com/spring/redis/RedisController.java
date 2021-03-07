package com.spring.redis;

import com.spring.redis.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class RedisController {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;


    //--------------------------------------------------------------------------------------
    @GetMapping("/getValue")
    public String getValue(@RequestParam("key") String key)
    {
    return (String) redisTemplate.opsForValue().get(key);
    }
    @PostMapping("/setValue")
    public void setValue(@RequestParam("key") String key,@RequestParam("value") String value,@RequestParam(value = "expiry",required = false) Long expiry)
    {
        if(expiry==null)
            redisTemplate.opsForValue().set(key,value);
        else
            redisTemplate.opsForValue().set(key,value, Duration.ofSeconds(expiry));
    }
    //------------------------------------------------------------------------------------
    @GetMapping("/lrange")
    public List<User>  getuserFromList(@RequestParam("key") String key,
                                       @RequestParam(value = "start",defaultValue = "0",required = false) int start,
                                       @RequestParam(value = "end",required = false,defaultValue = "-1") int end)
    {
     return redisTemplate.opsForList().range(key,start,end).stream().map(x->(User) x).collect(Collectors.toList());
    }

    @PostMapping("/lpush")
    public void lpush(@RequestParam("key") String key, @RequestBody User user)
    {
        redisTemplate.opsForList().leftPush(key,user);
    }
    @PostMapping("/rpush")
    public void rpush(@RequestParam("key") String key, @RequestBody User user)
    {
        redisTemplate.opsForList().rightPush(key,user);
    }
    @GetMapping("/lpop")
    public User lpop(@RequestParam("key") String key)
    {
        return (User) redisTemplate.opsForList().leftPop(key);
    }

    @GetMapping("/rpop")
    public User rpop(@RequestParam("key") String key)
    {
        return (User) redisTemplate.opsForList().rightPop(key);
    }

//-----------------------------------------------------------------------------------------

    @GetMapping("/hgetall")
    public Map hgetall(@RequestParam("key") String key)
    {
        HashMap<Object,Object> mapToReturn=new HashMap<>();
        List<Object> fields= Arrays.asList("id","name","country","age");
        List<Object> values=redisTemplate.opsForHash().multiGet(key,fields);
        for(int i=0;i<values.size();i++)
        {
            mapToReturn.put(fields.get(i),values.get(i));
        }
        return mapToReturn;

    }

    @PutMapping("/hmset")
    public void hmset(@RequestParam("key") String key,@RequestBody User user)
    {
        HashMap<String,Object> fieldMap=new HashMap<>();
        fieldMap.put("age",user.getAge());
        fieldMap.put("country",user.getCountry());
        fieldMap.put("name",user.getName());
        fieldMap.put("id",user.getId());

        redisTemplate.opsForHash().putAll(key,fieldMap);

    }





}
