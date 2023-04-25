package com.lyu.service;

import com.lyu.entity.SearchCandidateWord;

import java.util.List;

/**
 * @author LEE
 * @time 2023/1/26 11:49
 */
public interface SearchCandidateWordService {
    /**
     * 获取按照搜索指数降序排列的最多20个候选词
     * @param keyword
     * @return
     */
    List<SearchCandidateWord> getCandidateWords(String keyword);

    /**
     * 将候选词保存到数据库，需进行安全检查
     * @param keyword
     */
    void saveOrUpdateCandidateWord(String keyword);

    /**
     * 更新搜索指数
     * @param searchCandidateWord
     * @return
     */
    Integer updateCandidateWordIndex(SearchCandidateWord searchCandidateWord);
}
