package com.agoni.blog.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.agoni.blog.model.dto.base.OutputConverter;
import com.agoni.blog.model.entity.Category;

import java.util.Date;

/**
 * Category output dto.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-19
 */
@Data
@ToString
@EqualsAndHashCode
public class CategoryDTO implements OutputConverter<CategoryDTO, Category> {

    private Integer id;

    private String name;

    private String slug;

    private String description;

    private String thumbnail;

    private Integer parentId;

    private Date createTime;

    private String fullPath;
}
