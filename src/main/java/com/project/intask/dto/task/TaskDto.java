package com.project.intask.dto.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.intask.model.Status;
import com.project.intask.model.Task;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @JsonIgnore
    private Long id;
    @NotNull
    @Size(min = 4)
    @ApiModelProperty(notes = "name", example = "shop", required = true)
    private String name;
    @ApiModelProperty(notes = "description", example = "buy onion, carrot, beer", required = true)
    private String description;
    @NotNull
    @ApiModelProperty(notes = "status", example = "HOLD", required = true)
    private Status status;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.status = task.getStatus();
    }

    public Task toModel() {
        Task task = new Task();
        task.setId(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        return task;
    }
}
