package com.core.gtd.src.basket.controller;

import com.core.gtd.src.common.response.BaseResponse;
import com.core.gtd.src.basket.model.dto.TaskDetailDto;
import com.core.gtd.src.basket.model.request.DetailCompleteRequest;
import com.core.gtd.src.basket.model.request.TaskDetailRequest;
import com.core.gtd.src.basket.model.response.TaskDetailResponse;
import com.core.gtd.src.basket.service.TaskDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.core.gtd.src.common.response.BaseResponse.response;

@Slf4j
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskDetailController {

    private final TaskDetailService taskDetailService;

    @PostMapping("/{taskId}/detail")
    public BaseResponse<TaskDetailResponse> createTaskDetail(@PathVariable("taskId") Long taskId,
                                                             @RequestBody TaskDetailRequest detailCreateRequest) {

        log.info("Post Mapping - Create a new taskDetail with task_id : {}," +
                 " Request Details : {}",
                taskId, detailCreateRequest);

        TaskDetailDto taskDetailDto = taskDetailService.createTaskDetail(taskId, detailCreateRequest);
        return response(TaskDetailResponse.fromDto(taskDetailDto));
    }

    @PutMapping("{taskId}/update/{taskDetailId}")
    public BaseResponse<TaskDetailResponse> updateTaskDetail(@PathVariable("taskId") Long taskId,
                                                             @PathVariable("taskDetailId") Long taskDetailId,
                                                             @RequestBody TaskDetailRequest detailUpdateRequest) {

        log.info("Put Mapping - Update taskDetail with task_id : {}," +
                 " detail_id : {}," +
                 " Request Details : {}",
                taskId, taskDetailId, detailUpdateRequest);

        TaskDetailDto taskDetailDto = taskDetailService.updateTaskDetail(taskId, taskDetailId, detailUpdateRequest);

        return response(TaskDetailResponse.fromDto(taskDetailDto));

    }

    @PutMapping("{taskId}/convert/{taskDetailId}")
    public BaseResponse<TaskDetailResponse> checkComplete(@PathVariable("taskId") Long taskId,
                                                          @PathVariable("taskDetailId") Long taskDetailId,
                                                          @RequestBody DetailCompleteRequest detailCompleteRequest) {

        log.info("Put Mapping - Check isComplete with task_id : {}," +
                 " detail_id : {}," +
                 " Request Details : {}",
                taskId, taskDetailId, detailCompleteRequest);

        TaskDetailDto taskDetailDto = taskDetailService.checkComplete(
                taskId,
                taskDetailId,
                detailCompleteRequest);

        System.out.println("taskDetailDto.getIsComplete() = " + taskDetailDto.getIsComplete());

        return response(TaskDetailResponse.fromDto(taskDetailDto));
    }

    @DeleteMapping("{taskId}/delete/{taskDetailId}")
    public BaseResponse<Integer> deleteTaskDetail(@PathVariable("taskId") Long taskId,
                                                  @PathVariable("taskDetailId") Long taskDetailId) {

        log.info("Delete Mapping - Delete task with taskId : {}, taskDetailId : {}", taskId ,taskDetailId);

        return response(taskDetailService.deleteTaskDetail(taskId, taskDetailId));
    }


}
