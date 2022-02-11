package com.occultus.learncase;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//@SpringBootTest
@Slf4j
class LearnCaseApplicationTests {

    @Test
    void contextLoads() {
        Set<Integer> set = new HashSet<>();
        System.out.println(isAnagram("anagram","nagaram"));
    }

    public boolean isAnagram(String s, String t) {
        boolean result = true;
        if(s.length() < 1 || t.length() > 50000) {
            return false;
        }
        Map<Character, Integer> map = new HashMap<>();

        for(char sChar : s.toCharArray()) {
            Integer value = map.getOrDefault(sChar, 0);
            value++;
            map.put(sChar, value);
        }
        for(char tChar : t.toCharArray()) {
            if(!map.containsKey(tChar)) {
                result = false;
                break;
            }
            Integer value = map.get(tChar);
            value--;
            map.put(tChar, value);
        }
        for(Map.Entry<Character,Integer> entry : map.entrySet()) {
            if(entry.getValue() != 0) {
                result = false;
            }
        }
        return result;
    }
}
