package org.example.my_first_spring_project_ever.DTO;

import org.example.my_first_spring_project_ever.Priority;
import org.example.my_first_spring_project_ever.Status;

public record TaskSearchFilter(
        Long creatorId,
        Long assignedUserId,
        Status status,
        Priority priority,
        int pageSize,
        int pageNum
) {}
