package com.lyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyu.cache.MybatisRedisCache;
import com.lyu.entity.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author LEE
 */
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 如果任一凭证（uid、username、phone）正确，返回用户信息；否则返回null
     *
     * @param user 被判断的用户
     * @return 用户信息或null
     */
//    User getUserIfExist(User user);

    /**
     * 保存用户到数据库
     * @param user 需要保存的用户
     * @return 受影响的行数
     */
//    Integer saveUser(User user);

    /**
     * 更新用户信息，无需更新的字段应为null
     * @param user 更改后的User
     * @return 受影响的行数
     */
//    Integer updateUser(User user);

//    User getUserByUid(Long uid);
}
