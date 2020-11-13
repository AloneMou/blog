package com.agoni.blog.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.agoni.blog.model.dto.CategoryDTO;
import com.agoni.blog.model.dto.TagDTO;
import com.agoni.blog.model.dto.post.BasePostSimpleDTO;

import java.util.List;
import java.util.Map;

/**
 * Post list vo.
 *
 * @author johnniang
 * @author guqing
 * @author ryanwang
 * @date 2019-03-19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PostListVO extends BasePostSimpleDTO {

    private Long commentCount;

    private List<TagDTO> tags;

    private List<CategoryDTO> categories;

    private Map<String, Object> metas;
}
