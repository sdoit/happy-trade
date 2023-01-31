package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.entity.SearchCandidateWord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/1/26 10:17
 */
@Mapper
public interface SearchCandidateWordMapper extends BaseMapper<SearchCandidateWord> {
    /**
     * 根据关键词获取候选词（20个）按搜索指数降序排列
     * @param keyword
     * @return
     */
//    List<SearchCandidateWord> getCandidateWords(String keyword);

    /**
     * 插入候选词，调用此方法之前必须安全检查候选词
     * @param searchCandidateWord
     * @return
     */
//    Integer insertCandidateWords(SearchCandidateWord searchCandidateWord);

    /**
     * 更新候选词搜索指数
     * @param searchCandidateWord
     * @return
     */
//    Integer updateCandidateWordIndex(SearchCandidateWord searchCandidateWord);
}
