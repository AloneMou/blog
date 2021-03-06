package com.agoni.blog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.agoni.blog.model.dto.base.OutputConverter;
import com.agoni.blog.model.entity.Option;

/**
 * Option output dto.
 *
 * @author johnniang
 * @date 3/20/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO implements OutputConverter<OptionDTO, Option> {

    private String key;

    private Object value;

}
