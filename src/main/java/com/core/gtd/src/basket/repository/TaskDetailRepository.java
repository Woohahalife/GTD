package com.core.gtd.src.basket.repository;

import com.core.gtd.src.basket.entity.TaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskDetailRepository extends JpaRepository<TaskDetail, Long> {

    @Query("""
            select td
            from TaskDetail td
            where td.id = :taskDetailId
            """)
    TaskDetail findByDetailId(@Param("taskDetailId") Long id);

//    @Modifying
//    @Query("update TaskDetail td set td.isComplete =:isComplete where td.id =:taskId and td.task.id =:taskDetailId")
//    void convertComplete(@Param("taskId") Long taskId,
//                                      @Param("taskDetailId") Long taskDetailId,
//                                      @Param("isComplete") IsComplete isComplete);

    @Modifying
    @Query("""
            UPDATE TaskDetail td
            SET td.state = 'DELETE'
            WHERE (td.task.id = :taskId and td.id = :taskDetailId)
            """)
    Optional<Integer> deleteBydetailId(@Param("taskId") Long id, @Param("taskDetailId") Long taskDetailId);
}
