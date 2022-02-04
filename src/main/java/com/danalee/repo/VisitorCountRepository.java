package com.danalee.repo;

import com.danalee.entity.VisitorCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorCountRepository extends JpaRepository<VisitorCountEntity, Integer> {
    VisitorCountEntity findByUserIdAndVisitDate(int userId, String today);
    List<VisitorCountEntity> findAllByUserId(int userId);
}
