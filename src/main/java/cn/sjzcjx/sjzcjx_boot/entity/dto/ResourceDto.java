package cn.sjzcjx.sjzcjx_boot.entity.dto;

import lombok.*;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/18 10:52
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ResourceDto {
    int id;
    String name;
    String otherName;
    String iconId;
    String comment;
    int official;
    String url;
}
