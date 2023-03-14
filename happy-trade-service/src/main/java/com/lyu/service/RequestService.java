package com.lyu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.entity.dto.RequestDTO;

import java.util.List;

/**
 * @author LEE
 * @time 2023/3/8 16:22
 */
public interface RequestService {
    /**
     * 用户发布需求（发布求购）
     *
     * @param request
     * @return 返回id
     */
    Long addRequest(RequestDTO request);

    /**
     * 修改求购信息
     *
     * @param request
     * @return
     */
    Integer updateRequest(RequestDTO request);
    /**
     * 下架求购
     *
     * @param id
     * @return
     */
    void takeDownRequest(Long id);
    /**
     * 上线求购
     *
     * @param id
     * @return
     */
    void uploadedRequest(Long id);

    /**
     * 删除指定求购
     *
     * @param id
     * @return
     */
    Integer deleteRequestById(Long id);

    /**
     * 根据商品id获取求购
     *
     * @param id
     * @return
     */
    RequestDTO getRequestById(Long id);


    /**
     * 获取指定用户求购列表
     *
     * @param page
     * @param uid
     * @return
     */
    List<RequestDTO> getRequestsFromUser(Page<RequestDTO> page, Long uid);

    /**
     * 根据关键词获取求购列表
     *
     * @param words
     * @param page
     * @return
     */
    List<RequestDTO> getRequestsByKeyWordsPage(String words, IPage<RequestDTO> page);

    /**
     * 获取指定分类的求购
     *
     * @param page
     * @param type
     * @return
     */
    List<RequestDTO> getRequestsByType(IPage<RequestDTO> page, Integer type);


    /**
     * 根据用户浏览内容，推荐商品
     *
     * @param page
     * @return
     */
    List<RequestDTO> getRequestsRecommendations(IPage<RequestDTO> page);

    /**
     * 返回最新求购
     *
     * @param page
     * @return
     */
    List<RequestDTO> getRequestsLatest(IPage<RequestDTO> page);



}
