package com.core.gtd.src.basket.model.request;

import com.core.gtd.common.constatnt.IsComplete;
import com.core.gtd.common.constatnt.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskDetailRequest {

    private String content;
    private State state;
    private IsComplete isComplete;
}
