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
@ApiModel(value = "Sort对象", description = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sort implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("分类名")
    private String name;

    @ApiModelProperty("英文名")
    private String sortName;

    @ApiModelProperty("父级")
    private Integer parent;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    private LocalDateTime createdAt;


}
