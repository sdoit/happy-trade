package com.lyu.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyu.entity.Tag;
import com.lyu.mapper.TagMapper;
import com.lyu.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            tagMapper.mapCommodityAndTags(tags.toArray(Tag[]::new), cid, LocalDateTime.now());
        }
    }

    @Override
    public List<Tag> getTagsLike(String keyword) {
        return tagMapper.selectList(new QueryWrapper<Tag>().likeRight("tag", keyword).last("limit 10"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTagsNotIncludedAndAddIncluded(Long cid, List<Tag> tags) {
        List<Tag> allTagsNow = tagMapper.getAllTagsByCid(cid);
        //需要删除的
        Tag[] tagsDelete = allTagsNow.stream().filter(tag -> !(tags.contains(tag))).toArray(Tag[]::new);
        //需要添加的
        Tag[] tagsNew = tags.stream().filter(tag -> !allTagsNow.contains(tag)).toArray(Tag[]::new);
        if (tagsNew.length > 0) {
            tagMapper.mapCommodityAndTags(tagsNew, cid, LocalDateTime.now());
        }
        if (tagsDelete.length > 0) {
            tagMapper.cancelAllTagsMap(cid, tagsDelete);
        }
    }
}
