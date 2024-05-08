package task.manager.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private String taskId;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private String projectName;
    private String description;
    private String priority;
    private String file;
}
