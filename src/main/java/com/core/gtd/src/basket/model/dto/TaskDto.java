package com.core.gtd.src.basket.model.dto;

import com.core.gtd.src.common.constatnt.Priority;
import com.core.gtd.src.common.constatnt.State;
import com.core.gtd.src.common.constatnt.TaskState;
import com.core.gtd.src.basket.entity.Task;
import com.core.gtd.src.users.model.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String content;
    private State state;
    private TaskState taskState;
    private String location;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startAt;
    private LocalDateTime deadlineAt;
    private List<TaskDetailDto> taskDetail = new ArrayList<>();
    private UserDto userDto;

    public static TaskDto fromEntity(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .content(task.getContent())
                .state(task.getState())
                .taskState(task.getTaskState())
                .location(task.getLocation())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .startAt(task.getStartAt())
                .deadlineAt(task.getDeadlineAt())
                .taskDetail(task.getTaskDetail().stream()
                                .map(TaskDetailDto::fromEntity)
                                .collect(Collectors.toList()))
                .userDto(UserDto.fromEntity(task.getUser()))
                .build();

    }
}
