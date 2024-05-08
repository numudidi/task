package task.manager.task.service;

import org.springframework.scheduling.config.Task;
import task.manager.task.model.Tasks;
import task.manager.task.model.dto.TaskDto;

import java.util.List;

public interface TaskService {
    public TaskDto createTask(Tasks tasks);
    public TaskDto updateTask(Tasks tasks);
    public TaskDto getTaskById(String taskId);
    public Tasks findTaskById(String taskId);
    public List<TaskDto> getAllTasks(String userId);
    void deleteTask(String taskId);
}
