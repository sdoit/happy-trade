package com.lyu;

import cn.hutool.core.util.StrUtil;
import com.lyu.common.Constant;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author LEE
 * @time 2023/1/12 14:36
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
public class IDTest {
    @Test
    public void IdCreateTest() {
        long groupID = 1;
        long i = 1;
        long idRaw = (System.currentTimeMillis() + Constant.ID_OFFSET);
        String format = StrUtil.format("{}{}", idRaw, groupID);
        String strNum = StrUtil.format("{}", 100000 + i);
        String substring = strNum.substring(1);
        Long newId = Long.parseLong(StrUtil.format("{}{}", format, substring));

    }
}

