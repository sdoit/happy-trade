package com.lyu;

import com.lyu.service.AlipayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WithdrawTest {
    @Resource
    private AlipayService alipayService;

    @Test
    public void testWithdraw() throws Exception {
//        alipayService.withdraw();
    }
}
