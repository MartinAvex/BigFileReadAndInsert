package com.xavier.repository;

import com.xavier.domian.StandardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @description: 用户Repository
 * @author: Martin.Avex
 * @date: 2024/8/2 9:07
 */
public interface StandardInfoRepository extends JpaRepository<StandardInfo, Long>, PagingAndSortingRepository<StandardInfo, Long> {
}
