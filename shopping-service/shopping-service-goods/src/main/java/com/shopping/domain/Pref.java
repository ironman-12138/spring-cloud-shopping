package com.shopping.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
@TableName("tb_pref")
@ApiModel(value="Pref对象", description="")
public class Pref implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "分类ID")
    private Integer cateId;

    @ApiModelProperty(value = "消费金额")
    private Integer buyMoney;

    @ApiModelProperty(value = "优惠金额")
    private Integer preMoney;

    @ApiModelProperty(value = "活动开始日期")
    private Date startTime;

    @ApiModelProperty(value = "活动截至日期")
    private Date endTime;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "状态")
    private String state;


}
