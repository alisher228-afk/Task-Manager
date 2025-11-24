package org.example.my_first_spring_project_ever.controller;

import jakarta.validation.Valid;
import org.example.my_first_spring_project_ever.Task;
import org.example.my_first_spring_project_ever.DTO.TaskSearchFilter;
import org.example.my_first_spring_project_ever.Priority;
import org.example.my_first_spring_project_ever.Status;
import org.example.my_first_spring_project_ever.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        log.info("REST: GET /tasks/{}", id);
        Task task = taskService.getById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<Page<Task>> searchTasks(
            @RequestParam(required = false) Long creatorId,
            @RequestParam(required = false) Long assignedUserId,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNum
    ) {
        log.info("REST: GET /tasks (search) creatorId={} assignedUserId={} status={} priority={} pageSize={} pageNum={}",
                creatorId, assignedUserId, status, priority, pageSize, pageNum);

        TaskSearchFilter filter = new TaskSearchFilter(creatorId, assignedUserId, status, priority, pageSize, pageNum);
        Page<Task> page = taskService.search(filter);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
        log.info("REST: POST /tasks create {}", task);
        Task created = taskService.create(task);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody @Valid Task task) {
        log.info("REST: PUT /tasks/{} update {}", id, task);
        Task updated = taskService.update(id, task);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("REST: DELETE /tasks/{}", id);
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Task> startTask(@PathVariable Long id) {
        log.info("REST: POST /tasks/{}/start", id);
        Task task = taskService.startTask(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
        log.info("REST: POST /tasks/{}/complete", id);
        Task task = taskService.completeTask(id);
        return ResponseEntity.ok(task);
    }
}
