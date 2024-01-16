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
        /*
          taskState가 COMPLETE, DIS_CONTINUE, PUT_OFF 인 상태를 제외한 task를 조회
          COMPLETE, DIS_CONTINUE, PUT_OFF 상태의 task는 따로 조회하기로 결정
          게시물 State는 ACTIVE 상태만 출력됨
         */
        List<TaskState> excludedTaskStates = Arrays.asList(
                TaskState.COMPLETE,
                TaskState.DIS_CONTINUE,
                TaskState.PUT_OFF
        );

        List<Task> taskList = taskRepository.findTaskByTaskStateNotInAndStateEquals(
                excludedTaskStates,
                State.ACTIVE);

        if (taskList.isEmpty()) {
            throw new AppException(EMPTY_TASKS);
        }

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
                taskRepository.findTaskByTaskStateEqualsAndStateEquals(taskState, State.ACTIVE);

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
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(TASK_DOES_NOT_EXIST));

        return TaskDto.fromEntity(task);
    }

    @Transactional
    public TaskDto updateTask(Long taskId, TaskRequest taskUpdateRequest) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(TASK_DOES_NOT_EXIST));

        TaskState taskState = getTaskStateAtStartTime(taskUpdateRequest);
        validateTaskStateAtDeadline(taskUpdateRequest);

        task.update(taskUpdateRequest, taskState);

        return TaskDto.fromEntity(task);
    }


    @Transactional
    public String deleteTask(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(TASK_DOES_NOT_EXIST));

        return null;

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



}
