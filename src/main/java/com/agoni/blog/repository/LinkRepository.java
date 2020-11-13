package com.agoni.blog.repository;

import org.springframework.data.jpa.repository.Query;
import com.agoni.blog.model.entity.Link;
import com.agoni.blog.repository.base.BaseRepository;

import java.util.List;

/**
 * Link repository.
 *
 * @author johnniang
 */
public interface LinkRepository extends BaseRepository<Link, Integer> {

    /**
     * Find all link teams.
     *
     * @return a list of teams
     */
    @Query(value = "select distinct a.team from Link a")
    List<String> findAllTeams();

    boolean existsByNameAndIdNot(String name, Integer id);

    boolean existsByUrlAndIdNot(String url, Integer id);
}
