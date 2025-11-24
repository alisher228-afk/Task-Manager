package org.example.my_first_spring_project_ever;

import org.example.my_first_spring_project_ever.Entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    int countByAssignedUserIdAndStatusIn(Long userId, List<Status> statuses);

    @Query("""
        SELECT t FROM TaskEntity t
        WHERE (:creatorId IS NULL OR t.creatorId = :creatorId)
          AND (:assignedUserId IS NULL OR t.assignedUserId = :assignedUserId)
          AND (:status IS NULL OR t.status = :status)
          AND (:priority IS NULL OR t.priority = :priority)
    """)
    Page<TaskEntity> search(
            @Param("creatorId") Long creatorId,
            @Param("assignedUserId") Long assignedUserId,
            @Param("status") Status status,
            @Param("priority") Priority priority,
            Pageable pageable
    );
}
