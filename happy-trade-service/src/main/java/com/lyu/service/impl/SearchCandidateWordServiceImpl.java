package com.lyu.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyu.entity.SearchCandidateWord;
import com.lyu.mapper.SearchCandidateWordMapper;
import com.lyu.service.SearchCandidateWordService;
import com.lyu.util.BadWordUtil;
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

    @Resource
    private BadWordUtil badWordUtil;

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
    public void saveOrUpdateCandidateWord(String keyword) {
        //安全检查，关键词不能包含敏感词
        if (badWordUtil.hasBadWord(keyword)) {
            return;
        }
        SearchCandidateWord searchCandidateWord = searchCandidateWordMapper.selectById(keyword);
        if (searchCandidateWord == null) {
            searchCandidateWord = new SearchCandidateWord();
            searchCandidateWord.setKeyword(keyword);
            searchCandidateWord.setSearchIndex(1L);
            searchCandidateWordMapper.insert(searchCandidateWord);
        } else {
            searchCandidateWord.setSearchIndex(searchCandidateWord.getSearchIndex() + 1);
            searchCandidateWordMapper.updateById(searchCandidateWord);
        }
    }

    @Override
    public Integer updateCandidateWordIndex(SearchCandidateWord searchCandidateWord) {
        return searchCandidateWordMapper.update(searchCandidateWord, new UpdateWrapper<SearchCandidateWord>()
                .eq("keyword", searchCandidateWord.getKeyword()));
    }

}
