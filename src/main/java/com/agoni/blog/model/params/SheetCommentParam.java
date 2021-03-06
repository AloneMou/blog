package com.agoni.blog.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import com.agoni.blog.model.entity.SheetComment;

/**
 * Sheet comment param.
 *
 * @author johnniang
 * @date 19-4-25
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetCommentParam extends BaseCommentParam<SheetComment> {

}
