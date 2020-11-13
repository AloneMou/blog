package com.agoni.blog.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.agoni.blog.model.dto.BaseCommentDTO;

import java.util.List;

/**
 * Base comment vo.
 *
 * @author johnniang
 * @date 19-4-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseCommentVO extends BaseCommentDTO {

    List<BaseCommentVO> children;
}
