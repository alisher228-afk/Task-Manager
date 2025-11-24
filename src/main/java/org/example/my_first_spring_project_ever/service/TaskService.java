package org.example.my_first_spring_project_ever.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.my_first_spring_project_ever.Entity.TaskEntity;
import org.example.my_first_spring_project_ever.Mapper.TaskMapper;
import org.example.my_first_spring_project_ever.Priority;
import org.example.my_first_spring_project_ever.Status;
import org.example.my_first_spring_project_ever.Task;
import org.example.my_first_spring_project_ever.TaskRepository;
import org.example.my_first_spring_project_ever.DTO.TaskSearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Task getById(Long id) {
        log.info("Service: getById({})", id);
        TaskEntity entity = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
        return taskMapper.toModel(entity);
    }

    public Page<Task> search(TaskSearchFilter filter) {
        log.info("Service: search(filter = {})", filter);
        int pageSize = Math.max(1, filter.pageSize());
        int pageNum = Math.max(0, filter.pageNum());
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "createDateTime"));

        Page<TaskEntity> page = taskRepository.search(
                filter.creatorId(),
                filter.assignedUserId(),
                filter.status(),
                filter.priority(),
                pageable
        );

        return page.map(taskMapper::toModel);
    }

    public Task create(Task dto) {
        log.info("Service: create(task) creatorId={}", dto.creatorId());
        if (dto.creatorId() == null) {
            throw new IllegalArgumentException("Task creator id is null");
        }
        TaskEntity entity = taskMapper.toEntity(dto);
        // ensure initial status and createDateTime if missing
        if (entity.getStatus() == null) {
            entity.setStatus(Status.CREATED);
        }
        if (entity.getCreateDateTime() == null) {
            entity.setCreateDateTime(LocalDate.now());
        }
        TaskEntity saved = taskRepository.save(entity);
        return taskMapper.toModel(saved);
    }

    public Task update(Long id, Task dto) {
        log.info("Service: update(id = {}, dto = {})", id, dto);
        TaskEntity existing = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        // If existing is DONE, only allow change when dto.status == IN_PROGRESS (to reopen)
        if (existing.getStatus() == Status.DONE) {
            if (dto.status() == null || dto.status() != Status.IN_PROGRESS) {
                throw new IllegalStateException("Cannot edit a DONE task unless returning it to IN_PROGRESS");
            }
        }

        // apply changes
        taskMapper.updateEntityFromDto(dto, existing);
        TaskEntity saved = taskRepository.save(existing);
        return taskMapper.toModel(saved);
    }

    public void delete(Long id) {
        log.info("Service: delete(id={})", id);
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    public Task startTask(Long id) {
        log.info("Service: startTask(id={})", id);
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        if (task.getAssignedUserId() == null) {
            throw new IllegalStateException("Assigned user id must be set before starting task");
        }

        Long userId = task.getAssignedUserId();

        int activeTasks = taskRepository.countByAssignedUserIdAndStatusIn(
                userId,
                List.of(Status.CREATED, Status.IN_PROGRESS)
        );

        if (activeTasks >= 5) {
            throw new IllegalStateException("User already has 5 active tasks");
        }

        task.setStatus(Status.IN_PROGRESS);
        TaskEntity saved = taskRepository.save(task);
        return taskMapper.toModel(saved);
    }

    public Task completeTask(Long id) {
        log.info("Service: completeTask(id={})", id);
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));

        if (task.getAssignedUserId() == null) {
            throw new IllegalArgumentException("assignedUserId must be set before completing task");
        }

        if (task.getDeadlineDate() == null) {
            throw new IllegalArgumentException("deadlineDate must be set before completing task");
        }

        if (task.getStatus() != Status.IN_PROGRESS) {
            throw new IllegalArgumentException("Task must be IN_PROGRESS to be completed");
        }

        task.setDoneDateTime(LocalDateTime.now());
        task.setStatus(Status.DONE);

        TaskEntity saved = taskRepository.save(task);
        return taskMapper.toModel(saved);
    }
}
