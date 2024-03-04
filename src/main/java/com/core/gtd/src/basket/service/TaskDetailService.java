package com.core.gtd.src.basket.service;

import com.core.gtd.src.common.constatnt.IsComplete;
import com.core.gtd.src.common.exception.AppException;
import com.core.gtd.src.basket.entity.Task;
import com.core.gtd.src.basket.entity.TaskDetail;
import com.core.gtd.src.basket.model.dto.TaskDetailDto;
import com.core.gtd.src.basket.model.request.DetailCompleteRequest;
import com.core.gtd.src.basket.model.request.TaskDetailRequest;
import com.core.gtd.src.basket.repository.TaskDetailRepository;
import com.core.gtd.src.basket.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.core.gtd.src.common.response.ResponseStatus.DELETE_IS_FAIL;
import static com.core.gtd.src.common.response.ResponseStatus.TASK_DOES_NOT_EXIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskDetailService {

    private final TaskRepository taskRepository;
    private final TaskDetailRepository taskDetailRepository;

    @Transactional
    public TaskDetailDto createTaskDetail(Long taskId,
                                          TaskDetailRequest detailCreateRequest) {

        Task task = taskRepository.find(taskId)
                .orElseThrow(() -> new AppException(TASK_DOES_NOT_EXIST));

        TaskDetail taskDetail = getTaskDetailFromRequest(task, detailCreateRequest);

        return TaskDetailDto.fromEntity(taskDetailRepository.save(taskDetail));
    }

    @Transactional
    public TaskDetailDto updateTaskDetail(Long taskId,
                                          Long taskDetailId,
                                          TaskDetailRequest detailUpdateRequest) {

        TaskDetail taskDetail = getTaskDetail(taskId, taskDetailId);

        taskDetail.update(detailUpdateRequest);

        return TaskDetailDto.fromEntity(taskDetail);
    }

    @Transactional
    public TaskDetailDto checkComplete(Long taskId,
                                       Long taskDetailId,
                                       DetailCompleteRequest detailCompleteRequest) {

        TaskDetail taskDetail = getTaskDetail(taskId, taskDetailId);

        if(detailCompleteRequest.getIsComplete() == IsComplete.DISCOMPLETE) {
            taskDetail.convert(IsComplete.COMPLETE);
        }else {
            taskDetail.convert(IsComplete.DISCOMPLETE);
        }

        return TaskDetailDto.fromEntity(taskDetail);
    }

    private TaskDetail getTaskDetail(Long taskId, Long taskDetailId) {
        taskRepository.findByIdActive(taskId)
                .orElseThrow(() -> new AppException(TASK_DOES_NOT_EXIST));

         return taskDetailRepository.findByDetailId(taskDetailId);
    }

    private TaskDetail getTaskDetailFromRequest(Task task,
                                                TaskDetailRequest detailCreateRequest) {

        return TaskDetail.builder()
                .content(detailCreateRequest.getContent())
                .state(detailCreateRequest.getState())
                .isComplete(detailCreateRequest.getIsComplete())
                .task(task)
                .build();
    }
    @Transactional
    public Integer deleteTaskDetail(Long taskId, Long taskDetailId) {

        return taskDetailRepository.deleteBydetailId(taskId, taskDetailId)
                .filter(result -> result != 0)
                .orElseThrow(() -> new AppException(DELETE_IS_FAIL));

    }
}
