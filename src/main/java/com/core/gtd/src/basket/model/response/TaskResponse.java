package com.core.gtd.src.basket.model.response;

import com.core.gtd.src.common.constatnt.Priority;
import com.core.gtd.src.common.constatnt.State;
import com.core.gtd.src.common.constatnt.TaskState;
import com.core.gtd.src.basket.model.dto.OnlyTaskDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private Long id;
    private String content;
    private State state;
    private TaskState taskState;
    private String location;
    private Priority priority;
    private LocalDateTime startAt;
    private LocalDateTime deadlineAt;

    public static TaskResponse fromDto(OnlyTaskDto taskDto) {
        return TaskResponse.builder()
                .id(taskDto.getId())
                .content(taskDto.getContent())
                .state(taskDto.getState())
                .taskState(taskDto.getTaskState())
                .location(taskDto.getLocation())
                .priority(taskDto.getPriority())
                .startAt(taskDto.getStartAt())
                .deadlineAt(taskDto.getDeadlineAt())
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
