package com.core.gtd.src.basket.model.response;

import com.core.gtd.src.common.constatnt.Priority;
import com.core.gtd.src.common.constatnt.State;
import com.core.gtd.src.common.constatnt.TaskState;
import com.core.gtd.src.basket.model.dto.TaskDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SelectedTaskResponse {

    private String content;
    private State state;
    private TaskState taskState;
    private String location;
    private Priority priority;
    private LocalDateTime startAt;
    private LocalDateTime deadlineAt;
    private List<TaskDetailResponse> taskDetail;

    public static SelectedTaskResponse fromDto(TaskDto taskDto) {
        return SelectedTaskResponse.builder()
                .content(taskDto.getContent())
                .state(taskDto.getState())
                .taskState(taskDto.getTaskState())
                .location(taskDto.getLocation())
                .priority(taskDto.getPriority())
                .startAt(taskDto.getStartAt())
                .deadlineAt(taskDto.getDeadlineAt())
                .taskDetail(taskDto.getTaskDetail()
                        .stream()
                        .map(TaskDetailResponse::fromDto)
                        .collect(Collectors.toList()))
                .build();
    }

//    public static TaskResponse fromDtoToUpdate(TaskDto taskDto) {
//        return TaskResponse.builder()
//                .title(taskDto.getTitle())
//                .content(taskDto.getContent())
//                .state(taskDto.getState())
//                .taskState(taskDto.getTaskState())
//                .location(taskDto.getLocation())
//                .priority(taskDto.getPriority())
//                .deadlineAt(taskDto.getDeadlineAt())
//                .build();
//    }





}
