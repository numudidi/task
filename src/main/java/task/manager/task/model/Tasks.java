package task.manager.task.model;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="tasks")
public class Tasks {
    @Id
    @Column(name = "task_id", unique = true)
    private String taskId;
    @Column(name = "title")
    private String name;
    @Column(name = "start_date")
    private Timestamp startDate;
    @Column(name = "end_date")
    private Timestamp endDate;
    @Column(name = "project")
    private String projectName;
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    @Column(name = "priority")
    private String priority;
    @Lob
    @Column(name = "file", columnDefinition = "LONGBLOB")
    private String file;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private Timestamp createdDate;
}
