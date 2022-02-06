package com.danalee.repo;

import com.danalee.entity.VisitorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Integer> {
    List<VisitorEntity> findAllByUserId(int userId);
    Page<VisitorEntity> findAllByUserIdOrderByVisitorIdDesc(int userId, Pageable pageable);
}
