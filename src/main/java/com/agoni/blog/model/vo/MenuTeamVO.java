package com.agoni.blog.model.vo;

import lombok.Data;
import lombok.ToString;
import com.agoni.blog.model.dto.MenuDTO;

import java.util.List;

/**
 * Menu team vo.
 *
 * @author ryanwang
 * @date 2019/8/28
 */
@Data
@ToString
public class MenuTeamVO {

    private String team;

    private List<MenuDTO> menus;
}
