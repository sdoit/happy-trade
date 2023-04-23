package com.lyu.service;

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
    void getDownCommodityForce(Long cid, String reason);

    /**
     * 强制下架商品，再次上架后需要经过审核
     *
     * @param cid
     * @param reason 强制删除原因
     */
    void deleteCommodityForce(Long cid, String reason);

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
