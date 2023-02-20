package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.entity.Tag;
import com.lyu.mapper.TagMapper;
import com.lyu.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author LEE
 * @time 2023/2/14 14:56
 */
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    @Override
    public Tag insertTagIfNotExist(Tag tag) {
        Tag tagInDb = tagMapper.selectOne(new QueryWrapper<Tag>().eq("tag", tag));
        if (tagInDb == null) {
            tagMapper.insert(tag);
            return tag;
        }
        return tagInDb;

    }

    @Override
    public List<Tag> insertTagsIfNotExist(List<Tag> tags) {
        List<Tag> tagsListResult = new ArrayList<>(tags.size());
        List<String> tagNames = new ArrayList<>(tags.size());
        for (Tag tag : tags) {
            tagNames.add(tag.getTag());
        }
        List<Tag> tagsInDb = tagMapper.selectList(new QueryWrapper<Tag>().in("tag", tagNames));
        tags.removeAll(tagsInDb);
        tagsListResult.addAll(tagsInDb);
        for (Tag tag : tags) {
            tag.setUid(StpUtil.getLoginIdAsLong());
            tag.setTime(LocalDateTime.now());
            tagMapper.insert(tag);
            tagsListResult.add(tag);
        }
        return tagsListResult;
    }

    @Override
    public void mapCommodityAndTags(List<Tag> tags, Long cid) {
        if (tags != null && !tags.isEmpty()) {
            tagMapper.mapCommodityAndTags(tags, cid, LocalDateTime.now());
        }
    }

    @Override
    public List<Tag> getTagsLike(String keyword) {
        return tagMapper.selectList(new QueryWrapper<Tag>().likeRight("tag", keyword).last("limit 10"));
    }

    @Override
    public void cancelTagsNotIncludedAndAddIncluded(Long cid, List<Tag> tags) {
        List<Tag> allTagsNow = tagMapper.getAllTagsByCid(cid);
        Collection<Tag> disjunction = CollectionUtil.disjunction(allTagsNow, tags);
        tagMapper.cancelAllTagsMap(cid, disjunction);
        tagMapper.mapCommodityAndTags(disjunction, cid, LocalDateTime.now());
    }
}
