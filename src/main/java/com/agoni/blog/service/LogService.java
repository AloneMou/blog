package com.agoni.blog.service;

import org.springframework.data.domain.Page;
import com.agoni.blog.model.dto.LogDTO;
import com.agoni.blog.model.entity.Log;
import com.agoni.blog.service.base.CrudService;

/**
 * Log service interface.
 *
 * @author johnniang
 * @date 2019-03-14
 */
public interface LogService extends CrudService<Log, Long> {

    /**
     * Lists latest logs.
     *
     * @param top top number must not be less than 0
     * @return a page of latest logs
     */
    Page<LogDTO> pageLatest(int top);
}
