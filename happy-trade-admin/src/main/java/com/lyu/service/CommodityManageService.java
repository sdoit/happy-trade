package com.lyu.service;

import com.lyu.common.reason.DeleteCommodityReason;
import com.lyu.common.reason.GetDownCommodityReason;

/**
 * @author LEE
 * @time 2023/4/10 10:19
 */
public interface CommodityManageService {
    /**
     * 强制下架商品，再次上架后需要经过审核
     *
     * @param cid
     * @param reason 强制下架原因
     */
    void getDownCommodityForce(Long cid, GetDownCommodityReason reason);

    /**
     * 强制下架商品，再次上架后需要经过审核
     *
     * @param cid
     * @param reason 强制删除原因
     */
    void deleteCommodityForce(Long cid, DeleteCommodityReason reason);

    /**
     * 撤销强制下架商品
     *
     * @param cid
     */

    void withdrawGetDownCommodityForce(Long cid);

    /**
     * 撤销强制删除商品
     *
     * @param cid
     */

    void withdrawDeleteCommodityForce(Long cid);
}
