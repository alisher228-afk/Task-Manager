package org.example.my_first_spring_project_ever.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.example.my_first_spring_project_ever.Priority;
import org.example.my_first_spring_project_ever.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "Tasks")
@Entity
public class TaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    @Column(name = "Creator_Id" , nullable = false)
    Long creatorId;
    @Column(name = "assignedUserId" ,  nullable = false)
    Long assignedUserId;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    Status status;
    @NotNull
    @Column(name = "createDateTime"  , nullable = false)
    LocalDate createDateTime;
    @NotNull
    @Future
    @Column(name = "deadlineDate"   , nullable = false)
    LocalDate deadlineDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "priority"  , nullable = false)
    Priority priority;
    @Column(name = "doneDateTime")
    private LocalDateTime doneDateTime;

    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }

    public void setDoneDateTime(LocalDateTime doneDateTime) {
        this.doneDateTime = doneDateTime;
    }

    public TaskEntity() {
    }

    public TaskEntity(Long id, Long creatorId, Long assignedUserId, Status status, LocalDate createDateTime, LocalDate deadlineDate, Priority priority) {
        this.id = id;
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createDateTime = createDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreateDateTime() {
        return createDateTime;
            }

    public void setCreateDateTime(LocalDate createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
