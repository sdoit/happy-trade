package com.lyu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyu.entity.SearchCandidateWord;
import com.lyu.mapper.SearchCandidateWordMapper;
import com.lyu.service.SearchCandidateWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author LEE
 * @time 2023/1/26 12:02
 */
@Service
@Slf4j
public class SearchCandidateWordServiceImpl implements SearchCandidateWordService {
    @Resource
    private SearchCandidateWordMapper searchCandidateWordMapper;

    @Override
    public List<SearchCandidateWord> getCandidateWords(String keyword) {
        if (StrUtil.isBlank(keyword)) {
            return null;
        }
        return searchCandidateWordMapper.selectList(new QueryWrapper<SearchCandidateWord>()
                .likeRight("keyword", keyword)
                .orderByDesc("search_index")
                .last("limit 20"));
    }

    @Override
    public Integer saveCandidateWord(SearchCandidateWord searchCandidateWord) {

        return searchCandidateWordMapper.insert(searchCandidateWord);
    }

    @Override
    public Integer updateCandidateWordIndex(SearchCandidateWord searchCandidateWord) {
        return searchCandidateWordMapper.update(searchCandidateWord, new UpdateWrapper<SearchCandidateWord>()
                .eq("keyword", searchCandidateWord.getKeyword()));
    }

}
