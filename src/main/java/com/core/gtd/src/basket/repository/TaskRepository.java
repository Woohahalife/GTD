package com.core.gtd.src.basket.repository;

import com.core.gtd.common.constatnt.State;
import com.core.gtd.common.constatnt.TaskState;
import com.core.gtd.src.basket.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findTaskByTaskStateNotInAndStateEquals(Collection<TaskState> taskState, State state);
    List<Task> findTaskByTaskStateEqualsAndStateEquals(TaskState taskState, State state);
}
