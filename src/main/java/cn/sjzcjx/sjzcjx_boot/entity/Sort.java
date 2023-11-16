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
 *
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@Getter
@Setter
@ApiModel(value = "Sort对象", description = "")
@AllArgsConstructor
public class Sort implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty("分类名")
    private String name;

    @ApiModelProperty("英文名")
    private String sortName;

    @ApiModelProperty("父级")
    private Integer parent;

    private Integer updatedAt;

    private Integer deletedAt;

    private LocalDateTime createdAt;


}
