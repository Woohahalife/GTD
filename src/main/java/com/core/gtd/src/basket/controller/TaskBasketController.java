package com.core.gtd.src.basket.controller;

import com.core.gtd.common.constatnt.TaskState;
import com.core.gtd.common.response.BaseResponse;
import com.core.gtd.src.basket.model.dto.OnlyTaskDto;
import com.core.gtd.src.basket.model.dto.TaskDto;
import com.core.gtd.src.basket.model.request.TaskRequest;
import com.core.gtd.src.basket.model.response.SelectedTaskResponse;
import com.core.gtd.src.basket.model.response.TaskDetailResponse;
import com.core.gtd.src.basket.model.response.TaskResponse;
import com.core.gtd.src.basket.service.TaskBasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.core.gtd.common.response.BaseResponse.response;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskBasketController {

    public final TaskBasketService taskBasketService;

    @PostMapping("")
    public BaseResponse<TaskResponse> createTask(
            @RequestBody TaskRequest taskCreateRequest
    ) {

        log.info("Post Mapping - Create a new task for user_id(user 생성X), Request Details : {}",
                taskCreateRequest);

        OnlyTaskDto taskDto = taskBasketService.createTask(taskCreateRequest);

        return response(TaskResponse.fromDto(taskDto));
    }

    @GetMapping("/all")
    public BaseResponse<List<TaskResponse>> allTasks() {

        log.info("Get Mapping - Read a all tasks");

        List<TaskResponse> taskResponseList = taskBasketService.allTasks().stream()
                .map(TaskResponse::fromDto)
                .collect(Collectors.toList());

        return response(taskResponseList);
    }

    @GetMapping("/detail/{taskId}")
    public BaseResponse<SelectedTaskResponse> getTask(@PathVariable(name = "taskId") Long taskId) {

        log.info("Get Mapping - Get task with ID : {}", taskId);

        TaskDto taskDto = taskBasketService.getTask(taskId);

        return response(SelectedTaskResponse.fromDto(taskDto));
    }

    @PutMapping("/task-update/{taskId}")
    public BaseResponse<TaskResponse> updateTask(
            @PathVariable(name = "taskId") Long taskId,
            @RequestBody TaskRequest taskUpdateRequest) {

        log.info("Put Mapping - Update task with ID : {}, detail : {}", taskId, taskUpdateRequest);

        OnlyTaskDto taskDto = taskBasketService.updateTask(taskId, taskUpdateRequest);

        return response(TaskResponse.fromDto(taskDto));
    }

    @GetMapping("/all/{taskState}")
    public BaseResponse<List<TaskResponse>> getTaskswithTaskState(
            @PathVariable(name = "taskState") TaskState taskState) {

        log.info("Get Mapping - Read tasks with param : {}", taskState);

        List<TaskResponse> taskResponseList = taskBasketService.getTasksWithTaskState(taskState)
                .stream()
                .map(TaskResponse::fromDto)
                .collect(Collectors.toList());

        return response(taskResponseList);
    }

    @DeleteMapping("/delete/{taskId}")
    public BaseResponse<Integer> deleteTask(@PathVariable(name = "taskId") Long taskId) {

        log.info("Delete Mapping - Delete task with param : {}", taskId);

        return response(taskBasketService.deleteTask(taskId));
    }
}
