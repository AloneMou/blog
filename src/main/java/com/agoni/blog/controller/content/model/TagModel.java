package com.agoni.blog.controller.content.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.agoni.blog.model.dto.TagDTO;
import com.agoni.blog.model.entity.Post;
import com.agoni.blog.model.entity.Tag;
import com.agoni.blog.model.enums.PostStatus;
import com.agoni.blog.model.vo.PostListVO;
import com.agoni.blog.service.*;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Tag Model.
 *
 * @author ryanwang
 * @date 2020-01-11
 */
@Component
public class TagModel {

    private final TagService tagService;

    private final PostService postService;

    private final PostTagService postTagService;

    private final OptionService optionService;

    private final ThemeService themeService;

    public TagModel(TagService tagService, PostService postService, PostTagService postTagService, OptionService optionService, ThemeService themeService) {
        this.tagService = tagService;
        this.postService = postService;
        this.postTagService = postTagService;
        this.optionService = optionService;
        this.themeService = themeService;
    }

    public String list(Model model) {
        model.addAttribute("is_tags", true);
        model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        model.addAttribute("meta_description", optionService.getSeoDescription());
        return themeService.render("tags");
    }

    public String listPost(Model model, String slug, Integer page) {
        // Get tag by slug
        final Tag tag = tagService.getBySlugOfNonNull(slug);
        TagDTO tagDTO = tagService.convertTo(tag);

        final Pageable pageable = PageRequest.of(page - 1, optionService.getArchivesPageSize(), Sort.by(DESC, "createTime"));
        Page<Post> postPage = postTagService.pagePostsBy(tag.getId(), PostStatus.PUBLISHED, pageable);
        Page<PostListVO> posts = postService.convertToListVo(postPage);

        model.addAttribute("is_tag", true);
        model.addAttribute("posts", posts);
        model.addAttribute("tag", tagDTO);
        model.addAttribute("meta_keywords", optionService.getSeoKeywords());
        model.addAttribute("meta_description", optionService.getSeoDescription());
        return themeService.render("tag");
    }
}
