package task.manager.task.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.manager.task.authmodule.model.UserEntity;
import task.manager.task.authmodule.service.AuthService;
import task.manager.task.model.TaskAssignment;
import task.manager.task.model.Tasks;
import task.manager.task.model.dto.GenericResponse;
import task.manager.task.model.dto.TaskDto;
import task.manager.task.service.TaskAssignmentService;
import task.manager.task.service.TaskService;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskAssignmentService taskAssignmentService;
    @Autowired
    private AuthService authService;

    @PostMapping("/record")
    public ResponseEntity<GenericResponse> recordTask(@RequestBody TaskDto taskDto, Principal principal){
        try {
            Tasks tasks = new ModelMapper().map(taskDto, Tasks.class);
            tasks.setCreatedBy(authService.getUserDetails(principal.getName()).getUserId());
            tasks.setCreatedDate(new Timestamp(new Date().getTime()));
            TaskDto newTask = taskService.createTask(tasks);
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("Task was created Successfully!!")
                    .status(HttpStatus.CREATED)
                    .data(newTask)
                    .build(), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("An error occurred when trying to register task")
                    .status(HttpStatus.BAD_REQUEST)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("update")
    public ResponseEntity<GenericResponse> updateTask(@RequestBody TaskDto taskDto, Principal principal){
        try {
            Tasks tasks = taskService.findTaskById(taskDto.getTaskId());
            tasks.setName(taskDto.getName());
            tasks.setDescription(taskDto.getDescription());
            tasks.setStartDate(taskDto.getStartDate());
//            task.setCreatedBy(usersRepository.findUserEntitiesByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get().getUserId());
            tasks.setCreatedBy(authService.getUserDetails(principal.getName()).getUserId());
            tasks.setCreatedDate(new Timestamp(new Date().getTime()));
            TaskDto newTask = taskService.updateTask(tasks);
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("Task was updated Successfully!!")
                    .status(HttpStatus.CREATED)
                    .data(newTask)
                    .build(), HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("An error occurred when trying to update task")
                    .status(HttpStatus.BAD_REQUEST)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/delete/{taskId}")
    public ResponseEntity<GenericResponse> deleteTask(@PathVariable String taskId){
        try {
            if(taskService.getTaskById(taskId) == null){
                return new ResponseEntity<>(GenericResponse.builder()
                        .message("Task not found with id " + taskId)
                        .status(HttpStatus.NOT_FOUND)
                        .build(), HttpStatus.NOT_FOUND);
            }
            taskService.deleteTask(taskId);
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("Task was deleted Successfully!!")
                    .status(HttpStatus.OK)
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("An error occurred when trying to delete task")
                    .status(HttpStatus.BAD_REQUEST)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<GenericResponse> fetchAllTasks(Principal principal){
        try {
            List<TaskDto> tasksList = taskService.getAllTasks(authService.getUserDetails(principal.getName()).getUserId());
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("Tasks was fetched Successfully!!")
                    .data(tasksList)
                    .status(HttpStatus.OK)
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("An error occurred when trying to fetch tasks")
                    .status(HttpStatus.OK)
                    .build(), HttpStatus.OK);
        }
    }

    @GetMapping("/get-task/{taskId}")
    public ResponseEntity<GenericResponse> getTask(@PathVariable String taskId){
        try {
            TaskDto task = taskService.getTaskById(taskId);
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("Task was fetched Successfully!!")
                    .data(task)
                    .status(HttpStatus.OK)
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("An error occurred when trying to fetch task")
                    .status(HttpStatus.OK)
                    .build(), HttpStatus.OK);
        }
    }

    @PostMapping("/assign-task/{taskId}/{userId}")
    public ResponseEntity<GenericResponse> assignTask(@PathVariable String taskId, @PathVariable String userId, Principal principal){
        try {
            TaskAssignment taskAssignment = new TaskAssignment();
            taskAssignment.setTaskId(taskId);
            taskAssignment.setUserId(userId);
            taskAssignment.setAssignedBy(authService.getUserDetails(principal.getName()).getUserId());
            taskAssignment = taskAssignmentService.save(taskAssignment);
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("Task was assigned Successfully!!")
                    .status(HttpStatus.OK)
                    .build(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(GenericResponse.builder()
                    .message("An error occurred when trying to assign task")
                    .status(HttpStatus.OK)
                    .build(), HttpStatus.OK);
        }
    }
}
