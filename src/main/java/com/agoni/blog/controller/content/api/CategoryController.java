package com.agoni.blog.controller.content.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import com.agoni.blog.model.dto.CategoryDTO;
import com.agoni.blog.model.dto.post.BasePostSimpleDTO;
import com.agoni.blog.model.entity.Category;
import com.agoni.blog.model.entity.Post;
import com.agoni.blog.model.enums.PostStatus;
import com.agoni.blog.service.CategoryService;
import com.agoni.blog.service.PostCategoryService;
import com.agoni.blog.service.PostService;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Content category controller.
 *
 * @author ryanwang
 * @date 2019-06-09
 */
@RestController("ApiContentCategoryController")
@RequestMapping("/api/content/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final PostCategoryService postCategoryService;

    private final PostService postService;

    public CategoryController(CategoryService categoryService,
            PostCategoryService postCategoryService,
            PostService postService) {
        this.categoryService = categoryService;
        this.postCategoryService = postCategoryService;
        this.postService = postService;
    }

    @GetMapping
    @ApiOperation("Lists categories")
    public List<? extends CategoryDTO> listCategories(@SortDefault(sort = "updateTime", direction = DESC) Sort sort,
            @RequestParam(name = "more", required = false, defaultValue = "false") Boolean more) {
        if (more) {
            return postCategoryService.listCategoryWithPostCountDto(sort);
        }
        return categoryService.convertTo(categoryService.listAll(sort));
    }

    @GetMapping("{slug}/posts")
    @ApiOperation("Lists posts by category slug")
    public Page<BasePostSimpleDTO> listPostsBy(@PathVariable("slug") String slug,
            @PageableDefault(sort = {"topPriority", "updateTime"}, direction = DESC) Pageable pageable) {
        // Get category by slug
        Category category = categoryService.getBySlugOfNonNull(slug);

        Page<Post> postPage = postCategoryService.pagePostBy(category.getId(), PostStatus.PUBLISHED, pageable);
        return postService.convertToSimple(postPage);
    }
}
