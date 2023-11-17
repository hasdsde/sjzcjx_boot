package cn.sjzcjx.sjzcjx_boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 位于本地的文件
 * </p>
 *
 * @author hasd
 * @since 2023-11-16
 */
@Getter
@Setter
@ApiModel(value = "File对象", description = "位于本地的文件")
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String type;

    @ApiModelProperty("链接")
    private String url;

    private String name;

    private String md5;
    private String version;

    @ApiModelProperty("大小")
    private String size;

    private Integer resourceId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;


}
