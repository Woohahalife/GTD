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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlyTaskDto {

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

    public static OnlyTaskDto fromEntity(Task task) {
        return OnlyTaskDto.builder()
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
                .build();
    }
}
