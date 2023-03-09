package com.example.test.dto.board;

import com.example.test.model.Board;
import com.example.test.model.Task;
import com.example.test.model.User;
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
    private Long id;
    @NotNull
    @Size(min = 3)
    private String name;
    private List<Task> tasks;
    private User user;

    public BoardDto(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.tasks = board.getTasks();
        this.user = board.getUser();
    }


    public Board toModel() {
        Board board = new Board();
        board.setId(id);
        board.setName(name);
        board.setTasks(tasks);
        board.setUser(user);
        return board;
    }

}
