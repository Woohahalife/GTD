package com.core.gtd.src.basket.model.response;

import com.core.gtd.common.constatnt.Priority;
import com.core.gtd.common.constatnt.State;
import com.core.gtd.common.constatnt.TaskState;
import com.core.gtd.src.basket.entity.TaskDetail;
import com.core.gtd.src.basket.model.dto.OnlyTaskDto;
import com.core.gtd.src.basket.model.dto.TaskDetailDto;
import com.core.gtd.src.basket.model.dto.TaskDto;
import lombok.*;

import javax.swing.tree.TreePath;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
