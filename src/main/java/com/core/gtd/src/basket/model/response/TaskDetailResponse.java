package com.core.gtd.src.basket.model.response;

import com.core.gtd.common.constatnt.IsComplete;
import com.core.gtd.common.constatnt.State;
import com.core.gtd.src.basket.model.dto.TaskDetailDto;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailResponse {

    private Long id;
    private String content;
    private State state;
    private IsComplete isComplete;
    private Long taskId;

    public static TaskDetailResponse fromDto(TaskDetailDto taskDetailDto) {
        return TaskDetailResponse.builder()
                .id(taskDetailDto.getId())
                .content(taskDetailDto.getContent())
                .state(taskDetailDto.getState())
                .isComplete(taskDetailDto.getIsComplete())
                .taskId(taskDetailDto.getTask().getId())
                .build();
    }
}

