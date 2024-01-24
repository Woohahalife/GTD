package com.core.gtd.src.basket.entity;

import com.core.gtd.common.constatnt.IsComplete;
import com.core.gtd.common.constatnt.State;
import com.core.gtd.common.entity.BaseEntity;
import com.core.gtd.src.basket.model.request.TaskDetailRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@ToString(callSuper = true)
@Builder
@AllArgsConstructor
@SQLRestriction("state <> 'DELETE'") // @Where 어노테이션 Deprecated(6.3)로 인한 대체
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private IsComplete isComplete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    public void update(TaskDetailRequest detailUpdateRequest) {
        this.content = detailUpdateRequest.getContent();
    }

    public void convert(IsComplete isComplete) {
        this.isComplete = isComplete;
    }
}
