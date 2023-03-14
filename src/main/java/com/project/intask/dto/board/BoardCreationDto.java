package com.project.intask.dto.board;

import com.project.intask.model.Board;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class BoardCreationDto {

    @NotNull
    @Size(min = 3)
    @ApiModelProperty(notes = "name", example = "testBoard", required = true)
    private String name;

    public Board toModel() {
        Board board = new Board();
        board.setName(name);
        return board;
    }

}
