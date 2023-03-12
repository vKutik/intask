package com.project.intask.controller.board;

import com.project.intask.dto.board.BoardDto;
import com.project.intask.dto.task.TaskDto;
import com.project.intask.model.Board;
import com.project.intask.service.board.BoardService;
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
public class RestApiBoardController {

    private final BoardService boardService;

    @GetMapping
    public List<BoardDto> findAll() {
        return boardService
            .getAllBoardsByUsername(getUsernameFromContext())
            .stream()
            .map(BoardDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BoardDto getByIdBoard(@PathVariable Long id) {
        return new BoardDto(boardService.getBoardByIdAndUsername(id, getUsernameFromContext()));
    }

    @PostMapping
    public BoardDto createBoard(@RequestBody @Valid BoardDto boardDto) {
        return new BoardDto(
            boardService.createBoardByUsername(boardDto.toModel(), getUsernameFromContext()));
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoardByIdAndUsername(id, getUsernameFromContext());
    }

    @PutMapping("/{id}")
    public BoardDto updateBoard(@PathVariable Long id,
            @RequestBody @Valid BoardDto boardDto) {

        Board board = boardDto.toModel();
        board.setId(id);

        return new BoardDto(boardService.updateBoardByIdAndUsername(board, id, getUsernameFromContext()));
    }

    @PostMapping("/{id}/tasks")
    public BoardDto addTaskToBoard(@PathVariable Long id,
            @RequestBody @Valid TaskDto taskDto) {

        return new BoardDto(boardService
            .addTaskToBoardByIdAndUsername(
                id,
                taskDto.toModel(),
                getUsernameFromContext()));
    }

    @PutMapping("/{boardId}/tasks/{taskId}")
    public TaskDto updateTaskFromBoard(@PathVariable Long boardId,
            @PathVariable Long taskId,
            @RequestBody @Valid TaskDto taskDto) {

        return new TaskDto(boardService
            .updateTaskByBoardIdAndUsernameFromBoard(boardId, taskId, taskDto.toModel(),
                getUsernameFromContext()));
    }

    @GetMapping("/{boardId}/tasks/{taskId}")
    public TaskDto getTaskByIdFromBoard(@PathVariable Long boardId,
            @PathVariable Long taskId) {
        return new TaskDto(
            boardService.getTaskByIdAndUsernameFromBoard(boardId, taskId, getUsernameFromContext()));
    }

    @DeleteMapping("/{boardId}/tasks/{taskId}")
    public void deleteTask(@PathVariable Long boardId, @PathVariable Long taskId) {
        boardService.deleteTaskFromBoard(boardId, taskId, getUsernameFromContext());
    }

    @GetMapping("/{id}/tasks")
    public List<TaskDto> getAllTasksFromBoard(@PathVariable Long id) {
        return boardService
            .getAllTaskFromBoardByUsername(id, getUsernameFromContext())
            .stream()
            .map(TaskDto::new)
            .collect(Collectors.toList());
    }

    private String getUsernameFromContext() {
        return  SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
