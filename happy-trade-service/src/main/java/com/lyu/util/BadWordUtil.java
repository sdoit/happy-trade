package com.lyu.util;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.dfa.SensitiveUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LEE
 * @time 2023/3/20 14:52
 */
@Component
public class BadWordUtil {

    public BadWordUtil(@Value("${badWord.filename}") String badWordFileName) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(badWordFileName);
        FileReader fileReader = new FileReader(classPathResource.getFile());
        List<String> badWords = fileReader.readLines();
        SensitiveUtil.init(badWords, true);
    }

    public String sensitiveProcess(String text) {
        if (text == null) {
            return null;
        }
        char[] textChar = text.toCharArray();
        AtomicInteger lastEnd = new AtomicInteger();
        if (SensitiveUtil.containsSensitive(text)) {
            List<String> sensitiveWords = SensitiveUtil.getFindedAllSensitive(text);
            sensitiveWords.forEach(sensitiveWord -> {
                int start = text.indexOf(sensitiveWord, lastEnd.get());
                int end = start + sensitiveWord.length();
                for (int i = start; i < textChar.length && i < end; i++) {
                    textChar[i] = '*';
                }
                lastEnd.set(end);
            });
        }
        return String.valueOf(textChar);
    }
    public boolean hasBadWord(String text) {
        return SensitiveUtil.containsSensitive(text);
    }

}
