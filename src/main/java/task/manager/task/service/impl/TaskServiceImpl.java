package task.manager.task.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.task.exceptions.TaskExistsException;
import task.manager.task.model.Tasks;
import task.manager.task.model.dto.TaskDto;
import task.manager.task.repository.AssignmentRepository;
import task.manager.task.repository.TaskRepository;
import task.manager.task.service.TaskService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Override
    public TaskDto createTask(Tasks tasks) {
        if(taskRepository.findByName(tasks.getName()).isPresent()){
            throw new TaskExistsException("Task with the same name already exists!");
        }
        tasks.setTaskId(UUID.randomUUID().toString());
        return new ModelMapper().map(taskRepository.save(tasks), TaskDto.class);
    }

    @Override
    public TaskDto updateTask(Tasks tasks) {
        if(taskRepository.findByName(tasks.getName()).isPresent()){
            throw new TaskExistsException("Task with the same name already exists!");
        }
        return new ModelMapper().map(taskRepository.save(tasks), TaskDto.class);
    }

    @Override
    public TaskDto getTaskById(String taskId) {
        return new ModelMapper().map(taskRepository.findById(taskId).get(), TaskDto.class);
    }

    @Override
    public Tasks findTaskById(String taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public List<TaskDto> getAllTasks(String userId) {
        List<TaskDto> allTasks = new ArrayList<>();

        List<TaskDto> taskDtos =  taskRepository.findAllTasksByAssignedUser(userId).stream().map(
                task -> new ModelMapper().map(task, TaskDto.class)).collect(Collectors.toList());
        allTasks.addAll(taskDtos);
        List<TaskDto> taskAssignedDtos = assignmentRepository.findAllAssignmentsByUserId(userId).stream().map(assigned -> new ModelMapper().map(findTaskById(assigned.getTaskId()), TaskDto.class)).collect(Collectors.toList());
        allTasks.addAll(taskAssignedDtos);
        return allTasks;
    }

    @Override
    public void deleteTask(String taskId) {
        taskRepository.deleteById(taskId);
    }
}
