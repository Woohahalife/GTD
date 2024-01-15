package com.core.gtd.src.basket.model.request;

import com.core.gtd.common.constatnt.Priority;
import com.core.gtd.common.constatnt.State;
import com.core.gtd.common.constatnt.TaskState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    private String title;
    private String content;
    private State state;
    private String location;
    private Priority priority;
    private LocalDateTime startAt;
    private LocalDateTime deadlineAt;

}
