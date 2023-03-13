package com.project.intask.dto.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.intask.model.Board;
import com.project.intask.model.Task;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
    @JsonIgnore
    private Long id;
    @NotNull
    @Size(min = 3)
    @ApiModelProperty(notes = "name", example = "testBoard", required = true)
    private String name;

    @JsonIgnore
    private List<Task> tasks;
    @JsonIgnore
    private String username;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.tasks = board.getTasks();
        this.username = board.getUser().getUsername();
    }

    public Board toModel() {
        Board board = new Board();
        board.setId(id);
        board.setName(name);
        board.setTasks(tasks);
        return board;
    }

}
