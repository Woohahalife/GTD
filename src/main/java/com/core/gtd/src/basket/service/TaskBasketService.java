package com.core.gtd.src.basket.service;

import com.core.gtd.common.constatnt.State;
import com.core.gtd.common.constatnt.TaskState;
import com.core.gtd.common.exception.AppException;
import com.core.gtd.src.basket.entity.Task;
import com.core.gtd.src.basket.model.dto.TaskDto;
import com.core.gtd.src.basket.model.request.TaskRequest;
import com.core.gtd.src.basket.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostCommitUpdateEventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.core.gtd.common.response.ResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskBasketService {

    public final TaskRepository taskRepository;

    @Transactional
    public TaskDto createTask(TaskRequest taskCreateRequest) {

        Task task = getTaskFromRequest(taskCreateRequest);

        return TaskDto.fromEntity(taskRepository.save(task));
    }

    @Transactional
    public List<TaskDto> allTasks() {

        List<TaskState> taskStates = Arrays.asList(
                TaskState.BEFORE,
                TaskState.IN_PROGRESS
        );
        int updatedCount = taskRepository.updateTasksOverdue(taskStates);

        if (updatedCount == 0) {
            throw new AppException(EMPTY_TASKS); // 조회 대상이자 변경 대상인 task가 존재하지 않음
        }

        List<Task> taskList = taskRepository.findTasksInSpecificTaskStates(taskStates);

        return taskList.stream()
                .map(TaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<TaskDto> getTaskswithTaskState(TaskState taskState) {
        /*
         클라이언트로부터 taskState를 받아 데이터를 구분 출력
         */
        List<Task> taskList =
                taskRepository.findTaskByTaskState(taskState);

        if (taskList.isEmpty()) {
            throw new AppException(EMPTY_TASKS);
        }

        return taskList.stream()
                .map(TaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDto getTask(Long taskId) {
        /*
         task를 선택할 시 taskState에 상관없이 전부 조회 가능해야함
         */
        Task task = getTaskStateActive(taskId);

        return TaskDto.fromEntity(task);
    }

    @Transactional
    public TaskDto updateTask(Long taskId, TaskRequest taskUpdateRequest) {

        Task task = getTaskStateActive(taskId);

        TaskState taskState = getTaskStateAtStartTime(taskUpdateRequest);
        validateTaskStateAtDeadline(taskUpdateRequest);

        task.update(taskUpdateRequest, taskState);

        return TaskDto.fromEntity(task);
    }

    @Transactional
    public int deleteTask(Long taskId) {

        Task task = getTaskStateActive(taskId);

        return taskRepository.deleteByid(taskId)
                .filter(result -> result == 1)
                .orElseThrow(() -> new AppException(DELETE_IS_FAIL));

    }


    private Task getTaskFromRequest(TaskRequest taskRequest) {

        TaskState taskState = getTaskStateAtStartTime(taskRequest);

        validateTaskStateAtDeadline(taskRequest);

        return Task.builder()
                .title(taskRequest.getTitle())
                .content(taskRequest.getContent())
                .state(taskRequest.getState())
                .taskState(taskState)
                .location(taskRequest.getLocation())
                .priority(taskRequest.getPriority())
                .startAt(taskRequest.getStartAt())
                .deadlineAt(taskRequest.getDeadlineAt())
                .build();
    }

    private void validateTaskStateAtDeadline(TaskRequest taskRequest) {

        LocalDateTime deadline = taskRequest.getDeadlineAt();

        if (!deadline.isAfter(taskRequest.getStartAt()) ||
                !deadline.isAfter(LocalDateTime.now())) {
            throw new AppException(DEADLINE_IS_INCORRECT);
        }
    }

    private TaskState getTaskStateAtStartTime(TaskRequest taskRequest) {
        // TaskState가 Dis_CONTINUE, PUT_OFF, CONPLETE가 아닌 것 중에서 선별
        return LocalDateTime.now().isAfter(taskRequest.getStartAt())
                ? TaskState.IN_PROGRESS
                : TaskState.BEFORE;
    }

    private Task getTaskStateActive(Long taskid) {

        return taskRepository.findByIdActive(taskid)
                .orElseThrow(() -> new AppException(TASK_DOES_NOT_EXIST));
    }
}
