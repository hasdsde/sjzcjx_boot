package cn.sjzcjx.sjzcjx_boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@ApiModel(value = "Resource对象", description = "")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("别名")
    private String otherName;

    @ApiModelProperty("图表id")
    private String iconId;

    @ApiModelProperty("备注")
    private String comment;

    @ApiModelProperty("分类id")
    private Integer sortId;

    @ApiModelProperty("上传者")
    private String uploader;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;


}
