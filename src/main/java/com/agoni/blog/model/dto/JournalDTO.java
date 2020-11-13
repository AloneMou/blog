package com.agoni.blog.model.dto;

import lombok.Data;
import com.agoni.blog.model.dto.base.OutputConverter;
import com.agoni.blog.model.entity.Journal;
import com.agoni.blog.model.enums.JournalType;

import java.util.Date;

/**
 * Journal dto.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-24
 */
@Data
public class JournalDTO implements OutputConverter<JournalDTO, Journal> {

    private Integer id;

    private String sourceContent;

    private String content;

    private Long likes;

    private Date createTime;

    private JournalType type;
}
