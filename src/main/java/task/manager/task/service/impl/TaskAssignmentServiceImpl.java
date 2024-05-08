package task.manager.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.task.model.TaskAssignment;
import task.manager.task.repository.AssignmentRepository;
import task.manager.task.service.TaskAssignmentService;

import java.util.UUID;

@Service
public class TaskAssignmentServiceImpl implements TaskAssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Override
    public TaskAssignment save(TaskAssignment taskAssignment) {
        taskAssignment.setId(UUID.randomUUID().toString());
        return assignmentRepository.save(taskAssignment);
    }
}
