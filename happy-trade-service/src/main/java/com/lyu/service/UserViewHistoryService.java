package com.lyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.entity.Commodity;
import com.lyu.entity.UserViewHistory;
import com.lyu.entity.dto.UserViewHistoryDTO;

import java.util.List;

/**
 * @author LEE
 * @time 2023/1/31 19:26
 */

public interface UserViewHistoryService {
    /**
     * 保存浏览历史
     *
     * @param cid
     * @return
     */
    Commodity saveViewHistory(Long cid);

    /**
     * 获取某用户的所有浏览历史
     *
     * @param uid
     * @param page
     * @return 包含commodity对象
     */
    List<UserViewHistoryDTO> getAllViewHistoryByUid(Page<UserViewHistoryDTO> page, Long uid);

    /**
     * 判断这个浏览历史是否存在
     *
     * @param userViewHistory
     * @return
     */
    Boolean existViewHistory(UserViewHistory userViewHistory);

}
