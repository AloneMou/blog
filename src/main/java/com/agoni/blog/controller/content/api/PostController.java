package com.agoni.blog.controller.content.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import com.agoni.blog.cache.lock.CacheLock;
import com.agoni.blog.model.dto.BaseCommentDTO;
import com.agoni.blog.model.dto.post.BasePostSimpleDTO;
import com.agoni.blog.model.entity.Post;
import com.agoni.blog.model.entity.PostComment;
import com.agoni.blog.model.enums.CommentStatus;
import com.agoni.blog.model.enums.PostStatus;
import com.agoni.blog.model.params.PostCommentParam;
import com.agoni.blog.model.vo.*;
import com.agoni.blog.service.OptionService;
import com.agoni.blog.service.PostCommentService;
import com.agoni.blog.service.PostService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Content post controller.
 *
 * @author johnniang
 * @date 2019-04-02
 */
@RestController("ApiContentPostController")
@RequestMapping("/api/content/posts")
public class PostController {

    private final PostService postService;

    private final PostCommentService postCommentService;

    private final OptionService optionService;

    public PostController(PostService postService,
            PostCommentService postCommentService,
            OptionService optionService) {
        this.postService = postService;
        this.postCommentService = postCommentService;
        this.optionService = optionService;
    }

    @GetMapping
    @ApiOperation("Lists posts")
    public Page<PostListVO> pageBy(@PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) {
        Page<Post> postPage = postService.pageBy(PostStatus.PUBLISHED, pageable);
        return postService.convertToListVo(postPage);
    }

    @PostMapping(value = "search")
    @ApiOperation("Lists posts by keyword")
    public Page<BasePostSimpleDTO> pageBy(@RequestParam(value = "keyword") String keyword,
            @PageableDefault(sort = "createTime", direction = DESC) Pageable pageable) {
        Page<Post> postPage = postService.pageBy(keyword, pageable);
        return postService.convertToSimple(postPage);
    }

    @GetMapping("{postId:\\d+}")
    @ApiOperation("Gets a post")
    public PostDetailVO getBy(@PathVariable("postId") Integer postId,
            @RequestParam(value = "formatDisabled", required = false, defaultValue = "true") Boolean formatDisabled,
            @RequestParam(value = "sourceDisabled", required = false, defaultValue = "false") Boolean sourceDisabled) {
        PostDetailVO postDetailVO = postService.convertToDetailVo(postService.getById(postId));

        if (formatDisabled) {
            // Clear the format content
            postDetailVO.setFormatContent(null);
        }

        if (sourceDisabled) {
            // Clear the original content
            postDetailVO.setOriginalContent(null);
        }

        postService.publishVisitEvent(postDetailVO.getId());

        return postDetailVO;
    }

    @GetMapping("/slug")
    @ApiOperation("Gets a post")
    public PostDetailVO getBy(@RequestParam("slug") String slug,
            @RequestParam(value = "formatDisabled", required = false, defaultValue = "true") Boolean formatDisabled,
            @RequestParam(value = "sourceDisabled", required = false, defaultValue = "false") Boolean sourceDisabled) {
        PostDetailVO postDetailVO = postService.convertToDetailVo(postService.getBySlug(slug));

        if (formatDisabled) {
            // Clear the format content
            postDetailVO.setFormatContent(null);
        }

        if (sourceDisabled) {
            // Clear the original content
            postDetailVO.setOriginalContent(null);
        }

        postService.publishVisitEvent(postDetailVO.getId());

        return postDetailVO;
    }

    @GetMapping("{postId:\\d+}/comments/top_view")
    public Page<CommentWithHasChildrenVO> listTopComments(@PathVariable("postId") Integer postId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @SortDefault(sort = "createTime", direction = DESC) Sort sort) {

        return postCommentService.pageTopCommentsBy(postId, CommentStatus.PUBLISHED, PageRequest.of(page, optionService.getCommentPageSize(), sort));
    }


    @GetMapping("{postId:\\d+}/comments/{commentParentId:\\d+}/children")
    public List<BaseCommentDTO> listChildrenBy(@PathVariable("postId") Integer postId,
            @PathVariable("commentParentId") Long commentParentId,
            @SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        // Find all children comments
        List<PostComment> postComments = postCommentService.listChildrenBy(postId, commentParentId, CommentStatus.PUBLISHED, sort);
        // Convert to base comment dto

        return postCommentService.convertTo(postComments);
    }

    @GetMapping("{postId:\\d+}/comments/tree_view")
    @ApiOperation("Lists comments with tree view")
    public Page<BaseCommentVO> listCommentsTree(@PathVariable("postId") Integer postId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        return postCommentService.pageVosBy(postId, PageRequest.of(page, optionService.getCommentPageSize(), sort));
    }

    @GetMapping("{postId:\\d+}/comments/list_view")
    @ApiOperation("Lists comment with list view")
    public Page<BaseCommentWithParentVO> listComments(@PathVariable("postId") Integer postId,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        Page<BaseCommentWithParentVO> result = postCommentService.pageWithParentVoBy(postId, PageRequest.of(page, optionService.getCommentPageSize(), sort));
        return result;
    }

    @PostMapping("comments")
    @ApiOperation("Comments a post")
    @CacheLock(autoDelete = false, traceRequest = true)
    public BaseCommentDTO comment(@RequestBody PostCommentParam postCommentParam) {
        postCommentService.validateCommentBlackListStatus();

        // Escape content
        postCommentParam.setContent(HtmlUtils.htmlEscape(postCommentParam.getContent(), StandardCharsets.UTF_8.displayName()));
        return postCommentService.convertTo(postCommentService.createBy(postCommentParam));
    }

    @PostMapping("{postId:\\d+}/likes")
    @ApiOperation("Likes a post")
    public void like(@PathVariable("postId") Integer postId) {
        postService.increaseLike(postId);
    }
}
