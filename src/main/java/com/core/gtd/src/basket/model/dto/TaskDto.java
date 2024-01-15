package com.core.gtd.src.basket.model.dto;

import com.core.gtd.common.constatnt.Priority;
import com.core.gtd.common.constatnt.State;
import com.core.gtd.common.constatnt.TaskState;
import com.core.gtd.src.basket.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String title;
    private String content;
    private State state;
    private TaskState taskState;
    private String location;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime startAt;
    private LocalDateTime deadlineAt;

    public static TaskDto fromEntity(Task task) {
        return TaskDto.builder()
                .title(task.getTitle())
                .content(task.getContent())
                .state(task.getState())
                .taskState(task.getTaskState())
                .location(task.getLocation())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .startAt(task.getStartAt())
                .deadlineAt(task.getDeadlineAt())
                .build();
    }
}
