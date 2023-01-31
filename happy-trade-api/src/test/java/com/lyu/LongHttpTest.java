package com.lyu;

import com.lyu.controller.AliPayController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author LEE
 * @time 2023/1/13 19:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LongHttpTest {
    @Resource
    private AliPayController aliPayController;

    @Test
    public void WaitingTest(){

    }
}
