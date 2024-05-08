package task.manager.task.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="task_assignment")
public class TaskAssignment {
    @Id
    @Column(name = "id", unique = true)
    private String id;
    @Column(name = "task_id")
    private String taskId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "assigned_by")
    private String assignedBy;
}
