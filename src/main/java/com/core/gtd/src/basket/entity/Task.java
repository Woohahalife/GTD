package com.core.gtd.src.basket.entity;

import com.core.gtd.common.constatnt.Priority;
import com.core.gtd.common.constatnt.State;
import com.core.gtd.common.constatnt.TaskState;
import com.core.gtd.common.entity.BaseEntity;
import com.core.gtd.src.basket.model.request.TaskRequest;
import com.core.gtd.src.basket.model.request.TaskUpdateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private TaskState taskState;

    private String location;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(columnDefinition = "TIME")
    private LocalDateTime startAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deadlineAt;

    //private Long user_id;

    public void update(TaskUpdateRequest taskUpdateRequest) {
        this.title = taskUpdateRequest.getTitle();
        this.content = taskUpdateRequest.getContent();
        this.state = taskUpdateRequest.getState();
        this.taskState = taskUpdateRequest.getTaskState();
        this.location = taskUpdateRequest.getLocation();
        this.priority = taskUpdateRequest.getPriority();
        this.startAt = taskUpdateRequest.getStartAt();
        this.deadlineAt = taskUpdateRequest.getDeadlineAt();
    }
}
