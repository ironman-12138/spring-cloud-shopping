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
@TableName("tb_spu")
@ApiModel(value="Spu对象", description="")
public class Spu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @ApiModelProperty(value = "货号")
    private String sn;

    @ApiModelProperty(value = "SPU名")
    private String name;

    @ApiModelProperty(value = "副标题")
    private String caption;

    @ApiModelProperty(value = "品牌ID")
    private Integer brandId;

    @ApiModelProperty(value = "一级分类")
    private Integer category1Id;

    @ApiModelProperty(value = "二级分类")
    private Integer category2Id;

    @ApiModelProperty(value = "三级分类")
    private Integer category3Id;

    @ApiModelProperty(value = "模板ID")
    private Integer templateId;

    @ApiModelProperty(value = "运费模板id")
    private Integer freightId;

    @ApiModelProperty(value = "图片")
    private String image;

    @ApiModelProperty(value = "图片列表")
    private String images;

    @ApiModelProperty(value = "售后服务")
    private String saleService;

    @ApiModelProperty(value = "介绍")
    private String introduction;

    @ApiModelProperty(value = "规格列表")
    private String specItems;

    @ApiModelProperty(value = "参数列表")
    private String paraItems;

    @ApiModelProperty(value = "销量")
    private Integer saleNum;

    @ApiModelProperty(value = "评论数")
    private Integer commentNum;

    @ApiModelProperty(value = "是否上架")
    private String isMarketable;

    @ApiModelProperty(value = "是否启用规格")
    private String isEnableSpec;

    @ApiModelProperty(value = "是否删除")
    private String isDelete;

    @ApiModelProperty(value = "审核状态")
    private String status;


}
