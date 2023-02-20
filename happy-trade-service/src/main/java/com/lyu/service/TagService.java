package com.lyu.service;

import com.lyu.entity.Tag;

import java.util.List;

/**
 * @author LEE
 * @time 2023/2/14 14:35
 */
public interface TagService {
    /**
     * 如果标签不存在就插入标签
     *
     * @param tag
     * @return 携带tid的tag
     */
    Tag insertTagIfNotExist(Tag tag);

    /**
     * 批量插入
     *
     * @param tags
     * @return 携带tid的tag
     */
    List<Tag> insertTagsIfNotExist(List<Tag> tags);


    /**
     * 映射商品和tag
     *
     * @param tags
     * @param cid
     */
    void mapCommodityAndTags(List<Tag> tags, Long cid);

    /**
     * 模糊搜索tags
     *
     * @param keyword
     * @return
     */
    List<Tag> getTagsLike(String keyword);


    /**
     * 根据tags，取消目前商品不在tags中的标签标签，并且添加目前未被包含的标签
     *
     * @param cid  商品id
     * @param tags 更新后商品应包含的所有标签
     */
    void cancelTagsNotIncludedAndAddIncluded(Long cid, List<Tag> tags);
}
