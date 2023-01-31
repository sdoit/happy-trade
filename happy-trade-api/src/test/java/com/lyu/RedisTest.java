package com.lyu;

import com.lyu.common.Constant;
import com.lyu.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/1/12 12:40
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Resource
    private RedisUtil redisUtil;

    @Test
    public void RedisIOTest() {
        redisUtil.set(Constant.ORDER_LAST_MARK_KEY, 2);
        Object o = redisUtil.get(Constant.ORDER_LAST_MARK_KEY);
        System.out.println(o);

    }
}
