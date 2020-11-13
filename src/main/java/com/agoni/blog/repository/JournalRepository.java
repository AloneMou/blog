package com.agoni.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import com.agoni.blog.model.entity.Journal;
import com.agoni.blog.model.enums.JournalType;
import com.agoni.blog.repository.base.BaseRepository;

/**
 * Journal repository.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-22
 */
public interface JournalRepository extends BaseRepository<Journal, Integer>, JpaSpecificationExecutor<Journal> {

    /**
     * Finds journals by type and pageable.
     *
     * @param type     journal type must not be null
     * @param pageable page info must not be null
     * @return a page of journal
     */
    @NonNull
    Page<Journal> findAllByType(@NonNull JournalType type, @NonNull Pageable pageable);
}
