package org.example.my_first_spring_project_ever.Mapper;

import org.example.my_first_spring_project_ever.Entity.TaskEntity;
import org.example.my_first_spring_project_ever.Status;
import org.example.my_first_spring_project_ever.Task;
import org.springframework.stereotype.Component;


@Component
public class TaskMapper {

    // DTO -> Entity
    public TaskEntity toEntity(Task task) {
        if (task == null) return null;
        TaskEntity entity = new TaskEntity(
                task.id(),
                task.creatorId(),
                task.assignedUserId(),
                task.status(),
                // в entity поле называется createDateTime (LocalDate)
                task.startDate() == null ? null : task.startDate(),
                task.deadlineDate(),
                task.priority()
        );
        return entity;
    }

    // Entity -> DTO
    public Task toModel(TaskEntity entity) {
        if (entity == null) return null;
        return new Task(
                entity.getId(),
                entity.getCreatorId(),
                entity.getAssignedUserId(),
                entity.getStatus(),
                // map createDateTime -> startDate
                entity.getCreateDateTime(),
                entity.getDeadlineDate(),
                entity.getPriority()
        );
    }

    // Update entity fields from DTO (partial update helper)
    public void updateEntityFromDto(Task task, TaskEntity entity) {
        if (task == null || entity == null) return;
        if (task.creatorId() != null) entity.setCreatorId(task.creatorId());
        if (task.assignedUserId() != null) entity.setAssignedUserId(task.assignedUserId());
        if (task.status() != null) entity.setStatus(task.status());
        if (task.startDate() != null) entity.setCreateDateTime(task.startDate());
        if (task.deadlineDate() != null) entity.setDeadlineDate(task.deadlineDate());
        if (task.priority() != null) entity.setPriority(task.priority());
    }
}



