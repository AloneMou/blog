package com.agoni.blog.service.impl;

import org.springframework.stereotype.Service;
import com.agoni.blog.exception.ServiceException;
import com.agoni.blog.model.dto.StatisticDTO;
import com.agoni.blog.model.dto.StatisticWithUserDTO;
import com.agoni.blog.model.dto.UserDTO;
import com.agoni.blog.model.entity.User;
import com.agoni.blog.model.enums.CommentStatus;
import com.agoni.blog.model.enums.PostStatus;
import com.agoni.blog.service.*;

/**
 * Statistic service implementation.
 *
 * @author ryanwang
 * @date 2019-12-16
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private final PostService postService;

    private final SheetService sheetService;

    private final JournalService journalService;

    private final PostCommentService postCommentService;

    private final SheetCommentService sheetCommentService;

    private final JournalCommentService journalCommentService;

    private final OptionService optionService;

    private final LinkService linkService;

    private final CategoryService categoryService;

    private final TagService tagService;

    private final UserService userService;

    public StatisticServiceImpl(PostService postService,
            SheetService sheetService,
            JournalService journalService,
            PostCommentService postCommentService,
            SheetCommentService sheetCommentService,
            JournalCommentService journalCommentService,
            OptionService optionService,
            LinkService linkService,
            CategoryService categoryService,
            TagService tagService,
            UserService userService) {
        this.postService = postService;
        this.sheetService = sheetService;
        this.journalService = journalService;
        this.postCommentService = postCommentService;
        this.sheetCommentService = sheetCommentService;
        this.journalCommentService = journalCommentService;
        this.optionService = optionService;
        this.linkService = linkService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.userService = userService;
    }

    @Override
    public StatisticDTO getStatistic() {
        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setPostCount(postService.countByStatus(PostStatus.PUBLISHED));

        // Handle comment count
        long postCommentCount = postCommentService.countByStatus(CommentStatus.PUBLISHED);
        long sheetCommentCount = sheetCommentService.countByStatus(CommentStatus.PUBLISHED);
        long journalCommentCount = journalCommentService.countByStatus(CommentStatus.PUBLISHED);

        statisticDTO.setCommentCount(postCommentCount + sheetCommentCount + journalCommentCount);
        statisticDTO.setTagCount(tagService.count());
        statisticDTO.setCategoryCount(categoryService.count());
        statisticDTO.setJournalCount(journalService.count());

        long birthday = optionService.getBirthday();
        long days = (System.currentTimeMillis() - birthday) / (1000 * 24 * 3600);
        statisticDTO.setEstablishDays(days);
        statisticDTO.setBirthday(birthday);

        statisticDTO.setLinkCount(linkService.count());
        statisticDTO.setVisitCount(postService.countVisit() + sheetService.countVisit());
        statisticDTO.setLikeCount(postService.countLike() + sheetService.countLike());
        return statisticDTO;
    }

    @Override
    public StatisticWithUserDTO getStatisticWithUser() {

        StatisticDTO statisticDTO = getStatistic();

        StatisticWithUserDTO statisticWithUserDTO = new StatisticWithUserDTO();
        statisticWithUserDTO.convertFrom(statisticDTO);

        User user = userService.getCurrentUser().orElseThrow(() -> new ServiceException("未查询到博主信息"));
        statisticWithUserDTO.setUser(new UserDTO().convertFrom(user));

        return statisticWithUserDTO;
    }
}
