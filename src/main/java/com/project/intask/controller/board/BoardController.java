package com.project.intask.controller.board;

import com.project.intask.dto.board.BoardCreationDto;
import com.project.intask.dto.board.BoardDto;
import com.project.intask.dto.task.TaskCreationDto;
import com.project.intask.dto.task.TaskDto;
import com.project.intask.model.Board;
import com.project.intask.service.board.BoardService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    @ApiOperation(value = "get all boards")
    public List<BoardDto> findAll() {
        return boardService
            .getAllBoardsByUsername(getUsernameFromContext())
            .stream()
            .map(BoardDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get board by id")
    public BoardDto getByIdBoard(@PathVariable Long id) {
        return new BoardDto(boardService.getBoardByIdAndUsername(id, getUsernameFromContext()));
    }

    @PostMapping
    @ApiOperation(value = "create a new board")
    public BoardDto createBoard(@RequestBody @Valid BoardCreationDto boardCreationDto) {
        return new BoardDto(
            boardService.createBoardByUsername(
                    boardCreationDto.toModel(),
                    getUsernameFromContext()));
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoardByIdAndUsername(id, getUsernameFromContext());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "update board by id")
    public BoardDto updateBoard(@PathVariable Long id,
            @RequestBody @Valid BoardDto boardDto) {

        Board board = boardDto.toModel();
        board.setId(id);

        return new BoardDto(boardService.updateBoardByIdAndUsername(board,
            id,
            getUsernameFromContext()));
    }

    @PostMapping("/{boardId}/tasks")
    @ApiOperation(value = "create a new task by board's id")
    public BoardDto addTaskToBoard(@PathVariable Long boardId,
            @RequestBody @Valid TaskCreationDto taskCreationDto) {

        return new BoardDto(boardService
            .addTaskToBoardByIdAndUsername(
                boardId,
                taskCreationDto.toModel(),
                getUsernameFromContext()));
    }

    @PutMapping("/{boardId}/tasks/{taskId}")
    @ApiOperation(value = "update task by board's id and task's id")
    public TaskDto updateTaskFromBoard(@PathVariable Long boardId,
            @PathVariable Long taskId,
            @RequestBody @Valid TaskDto taskDto) {

        return new TaskDto(boardService
            .updateTaskByBoardIdAndUsernameFromBoard(boardId, taskId, taskDto.toModel(),
                getUsernameFromContext()));
    }

    @GetMapping("/{boardId}/tasks/{taskId}")
    @ApiOperation(value = "get task by board's id and task's id")
    public TaskDto getTaskByIdFromBoard(@PathVariable Long boardId,
            @PathVariable Long taskId) {
        return new TaskDto(
            boardService.getTaskByIdAndUsernameFromBoard(boardId,
                    taskId,
                    getUsernameFromContext()));
    }

    @DeleteMapping("/{boardId}/tasks/{taskId}")
    @ApiOperation(value = "delete task by board's id and task's id")
    public void deleteTask(@PathVariable Long boardId, @PathVariable Long taskId) {
        boardService.deleteTaskFromBoard(boardId, taskId, getUsernameFromContext());
    }

    @GetMapping("/{boardId}/tasks")
    @ApiOperation(value = "get all tasks")
    public List<TaskDto> getAllTasksFromBoard(@PathVariable Long boardId) {
        return boardService
            .getAllTaskFromBoardByUsername(boardId, getUsernameFromContext())
            .stream()
            .map(TaskDto::new)
            .collect(Collectors.toList());
    }

    private String getUsernameFromContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
