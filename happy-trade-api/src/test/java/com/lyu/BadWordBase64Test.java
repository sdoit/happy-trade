package com.lyu;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LEE
 * @time 2023/5/20 14:42
 */
@SpringBootTest
public class BadWordBase64Test {
    @Test
    public void TestBadWordBase64(){
        ClassPathResource classPathResource = new ClassPathResource("badwords.txt");
        String s = classPathResource.readUtf8Str();
        String[] split = s.split("\r\n");
        List<String> list = new ArrayList<>();
        List<String> listRaw = Arrays.asList(split);
        listRaw.forEach(s1 -> list.add(Base64.encode(s1)));
        File file = FileUtil.file("C:\\Users\\LEE\\Desktop\\log\\badwords.txt");
        FileUtil.appendLines(list,file,"UTF-8");
    }
}
