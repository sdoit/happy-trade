package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyu.entity.UserViewHistory;
import com.lyu.entity.dto.UserViewHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 * @time 2023/1/31 19:23
 */
@Mapper
public interface UserViewHistoryMapper extends BaseMapper<UserViewHistory> {
    /**
     * 获取某用户的所有浏览记录
     *
     * @param page
     * @param uid
     * @return 包含需要展示的所有信息 commodity cover
     */
    IPage<UserViewHistoryDTO> getAllViewHistoryByUid(Page<UserViewHistoryDTO> page, Long uid);


    /**
     * 判断某条浏览记录是否存在
     *
     * @param userViewHistory
     * @return
     */
    Boolean exists(UserViewHistory userViewHistory);
}
