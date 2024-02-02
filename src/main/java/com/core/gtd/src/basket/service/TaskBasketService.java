package com.core.gtd.src.basket.service;

import com.core.gtd.common.constatnt.TaskState;
import com.core.gtd.common.exception.AppException;
import com.core.gtd.src.basket.entity.Task;
import com.core.gtd.src.basket.model.dto.OnlyTaskDto;
import com.core.gtd.src.basket.model.dto.TaskDto;
import com.core.gtd.src.basket.model.request.TaskRequest;
import com.core.gtd.src.basket.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public OnlyTaskDto createTask(TaskRequest taskCreateRequest) {

        Task task = getTaskFromRequest(taskCreateRequest);

        return OnlyTaskDto.fromEntity(taskRepository.save(task));
    }

    @Transactional
    public List<OnlyTaskDto> allTasks() {

        List<TaskState> taskStates = Arrays.asList(
                TaskState.BEFORE,
                TaskState.IN_PROGRESS,
                TaskState.DIS_CONTINUE
        );

        // 마감 기한이 지난 task는 자동으로 DIS_CONTINUE 처리
        taskRepository.updateTasksOverdue(taskStates);

        List<Task> taskList =
                taskRepository.findTasksInSpecificTaskStates(taskStates);

        if (taskList.isEmpty()) {
            throw new AppException(EMPTY_TASKS);
        }

        return taskList.stream()
                .map(OnlyTaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OnlyTaskDto> getTasksWithTaskState(TaskState taskState) {
        /*
         클라이언트로부터 taskState를 받아 데이터를 구분 출력
         */
        List<Task> taskList =
                taskRepository.findTasksByTaskState(taskState);

        if (taskList.isEmpty()) {
            throw new AppException(EMPTY_TASKS);
        }

        return taskList.stream()
                .map(OnlyTaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskDto getTask(Long taskId) {
         //task를 선택할 시 taskState에 상관없이 전부 조회 가능
        Task task = getTaskStateActive(taskId);

        return TaskDto.fromEntity(task);
    }

    @Transactional
    public OnlyTaskDto updateTask(Long taskId, TaskRequest taskUpdateRequest) {

        Task task = getTaskStateActive(taskId); // 현재 taskDetail과 함께 조회하는 쿼리를 사용중 -> 추후 개별 task만 따로 조회하는 쿼리 만들어 컨텍스트 올릴 예정(01.25)

        TaskState taskState = getTaskStateAtStartTime(taskUpdateRequest);
        validateTaskStateAtDeadline(taskUpdateRequest);

        task.update(taskUpdateRequest, taskState);

        return OnlyTaskDto.fromEntity(task);
    }

    @Transactional
    public Integer deleteTask(Long taskId) {

        return taskRepository.deleteByid(taskId)
                .filter(result -> result == 1)
                .orElseThrow(() -> new AppException(DELETE_IS_FAIL));
    }

    private Task getTaskFromRequest(TaskRequest taskRequest) {

        TaskState taskState = getTaskStateAtStartTime(taskRequest);

        validateTaskStateAtDeadline(taskRequest);

        return Task.builder()
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
        // TaskState가 DIS_CONTINUE, PUT_OFF, CONPLETE가 아닌 것 중에서 선별
        return LocalDateTime.now().isAfter(taskRequest.getStartAt())
                ? TaskState.IN_PROGRESS
                : TaskState.BEFORE;
    }

    private Task getTaskStateActive(Long taskid) {

        return taskRepository.findByIdActive(taskid)
                .orElseThrow(() -> new AppException(TASK_DOES_NOT_EXIST));
    }
}
