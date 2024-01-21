package com.core.gtd.src.basket.entity;

import com.core.gtd.common.constatnt.Priority;
import com.core.gtd.common.constatnt.State;
import com.core.gtd.common.constatnt.TaskState;
import com.core.gtd.common.entity.BaseEntity;
import com.core.gtd.src.basket.model.request.TaskRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@SQLDelete(sql = "UPDATE task SET state = 'DELETE' where id = ?")
@SQLRestriction("state <> 'DELETE'") // @Where 어노테이션 Deprecated(6.3)로 인한 대체
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

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime startAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime deadlineAt;

    //private Long user_id;

    public void update(TaskRequest taskUpdateRequest,TaskState taskState) {
        this.title = taskUpdateRequest.getTitle();
        this.content = taskUpdateRequest.getContent();
        this.state = taskUpdateRequest.getState();
        this.taskState = taskState;
        this.location = taskUpdateRequest.getLocation();
        this.priority = taskUpdateRequest.getPriority();
        this.startAt = taskUpdateRequest.getStartAt();
        this.deadlineAt = taskUpdateRequest.getDeadlineAt();
    }

    public String delete(State state) {
        this.state = state;

        return "게시물이 삭제되었습니다.";
    }

}

