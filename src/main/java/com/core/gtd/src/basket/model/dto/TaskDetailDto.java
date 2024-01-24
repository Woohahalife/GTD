package com.core.gtd.src.basket.model.dto;

import com.core.gtd.common.constatnt.IsComplete;
import com.core.gtd.common.constatnt.State;
import com.core.gtd.src.basket.entity.Task;
import com.core.gtd.src.basket.entity.TaskDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailDto {

    private Long id;
    private String content;
    private State state;
    private IsComplete isComplete;
    private Task task;

    public static TaskDetailDto fromEntity(TaskDetail taskDetail) {
        return TaskDetailDto.builder()
                .id(taskDetail.getId())
                .content(taskDetail.getContent())
                .state(taskDetail.getState())
                .isComplete(taskDetail.getIsComplete())
                .task(taskDetail.getTask())
                .build();
    }

}
