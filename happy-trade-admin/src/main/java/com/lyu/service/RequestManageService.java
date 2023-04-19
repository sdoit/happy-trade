package com.lyu.service;

import com.lyu.common.reason.DeleteRequestReason;
import com.lyu.common.reason.GetDownRequestReason;

/**
 * @author LEE
 * @time 2023/4/10 10:21
 */
public interface RequestManageService {
    /**
     * 强制下架求购，再次上架后需要经过审核
     *
     * @param rid
     * @param reason 强制下架原因
     */
    void getDownRequestForce(Long rid, GetDownRequestReason reason);

    /**
     * 强制下架求购，再次上架后需要经过审核
     *
     * @param rid
     * @param reason 强制删除原因
     */
    void deleteRequestForce(Long rid, DeleteRequestReason reason);

    /**
     * 撤销强制下架求购
     *
     * @param rid
     */
    void withdrawGetDownRequestForce(Long rid);

    /**
     * 撤销强制删除求购
     *
     * @param rid
     */
    void withdrawDeleteRequestForce(Long rid);
}
