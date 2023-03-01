package com.lyu;

import com.lyu.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author LEE
 * @time 2023/1/12 12:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {


    @Test
    public void RedisIOTest() {
        RedisUtil.set("test", 2);
        Object o = RedisUtil.get("test");
        System.out.println(o);
    }
}
