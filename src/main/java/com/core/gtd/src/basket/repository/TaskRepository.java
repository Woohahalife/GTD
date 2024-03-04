package com.core.gtd.src.basket.repository;

import com.core.gtd.src.common.constatnt.TaskState;
import com.core.gtd.src.basket.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
            select t
            from Task t
            where t.taskState IN :taskStates
            """)
    List<Task> findTasksInSpecificTaskStates(@Param("taskStates") List<TaskState> taskStates);

    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE Task t
            SET t.taskState = 'DIS_CONTINUE'
            WHERE t.deadlineAt <= CURRENT_TIMESTAMP
            AND t.taskState IN :taskStates
            """)
    int updateTasksOverdue(@Param("taskStates") List<TaskState> taskStates);

    @Query("""
            select t
            from Task t
            where t.taskState = :taskState
            """)
    List<Task> findTasksByTaskState(@Param("taskState") TaskState taskState);

    @Query("""
            SELECT t
            FROM Task t
            left join fetch t.taskDetail td
            WHERE t.id = :taskId
            and (td.state = 'ACTIVE' or td is null)
            """)
    Optional<Task>  findByIdActive(@Param("taskId") Long id);

    @Query("""
            select t
            from Task t
            where t.id = :taskId
            """)
    Optional<Task> find(@Param("taskId") Long id);

    @Modifying
    @Query("""
            UPDATE Task t
            SET t.state = 'DELETE'
            WHERE t.id = :taskId
            """)
    Optional<Integer> deleteByid(@Param("taskId") Long id);
}
