package cn.sjzcjx.sjzcjx_boot.entity.dto;

import cn.sjzcjx.sjzcjx_boot.entity.Sort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/17 09:57
 **/


@Getter
@Setter
@Data
@AllArgsConstructor
public class SortDto extends Sort {
    ArrayList<Sort> children;

    public void addChildren(Sort s) {
        children.add(s);
    }

    public SortDto() {
        children = new ArrayList<>();
    }

    public SortDto(Integer id, String name, String sortName, Integer parent, LocalDateTime updatedAt, LocalDateTime deletedAt, LocalDateTime createdAt) {
        super(id, name, sortName, parent, updatedAt, deletedAt, createdAt);
    }
}
