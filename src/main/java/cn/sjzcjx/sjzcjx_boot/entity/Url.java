package cn.sjzcjx.sjzcjx_boot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资源URL
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@Getter
@Setter
@ApiModel(value = "Url对象", description = "资源URL")
@AllArgsConstructor
public class Url implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("来源")
    private String type;

    @ApiModelProperty("是否为官方url")
    private Integer official;

    @ApiModelProperty("是否本地下载")
    private Integer localDownload;

    @ApiModelProperty("腾讯云cos")
    private Integer cos;

    @ApiModelProperty("下载地址")
    private String url;

    @ApiModelProperty("资源id外键")
    private Integer resourceId;

    @ApiModelProperty("备注")
    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;


}
