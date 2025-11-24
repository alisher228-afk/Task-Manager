package org.example.my_first_spring_project_ever;

import jakarta.validation.constraints.*;

import java.time.LocalDate;


public record Task(
        @Null Long id,

        @NotNull Long creatorId,

        Long assignedUserId,

        Status status,

        LocalDate startDate,

        @Future LocalDate deadlineDate,

        @NotNull Priority priority
) {}

