package com.agoni.blog.model.params;

import lombok.Data;
import com.agoni.blog.model.enums.PostStatus;

/**
 * Post query.
 *
 * @author johnniang
 * @date 4/10/19
 */
@Data
public class PostQuery {

    /**
     * Keyword.
     */
    private String keyword;

    /**
     * Post status.
     */
    private PostStatus status;

    /**
     * Category id.
     */
    private Integer categoryId;

}
