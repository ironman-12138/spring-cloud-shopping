package com.shopping.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xcoder
 * @since 2021-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_spec")
@ApiModel(value="Spec对象", description="")
public class Spec implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "规格选项")
    private String options;

    @ApiModelProperty(value = "排序")
    private Integer seq;

    @ApiModelProperty(value = "模板ID")
    private Integer templateId;


}
