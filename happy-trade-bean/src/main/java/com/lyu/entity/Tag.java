package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author LEE
 * @time 2023/2/13 17:05
 */
@TableName("t_commodity_tag")
@Data
public class Tag {

    @TableId(type = IdType.AUTO)
    private Long tid;

    private String tag;
    private Long uid;
    private LocalDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tag)) {
            return false;
        }
        Tag tag1 = (Tag) o;
        return getTid().equals(tag1.getTid()) || getTag().equals(tag1.getTag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTag()) + Objects.hash(getTag());
    }
}
