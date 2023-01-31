package com.lyu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @TableName t_search_candidate_word
 */
@TableName(value ="t_search_candidate_word")
@Data
public class SearchCandidateWord implements Serializable {

    /**
     * 候选词
     */
    @NotBlank(message = "[候选词]不能为空")
    @Size(max = 20, message = "编码长度不能超过20")
    @ApiModelProperty("候选词")
    @Length(max = 20, message = "编码长度不能超过20")
    private String keyword;
    /**
     * 搜索指数，搜索次数越多搜索指数越大
     */
    @NotNull(message = "[搜索指数，搜索次数越多搜索指数越大]不能为空")
    @ApiModelProperty("搜索指数，搜索次数越多搜索指数越大")
    private Long searchIndex;


}
