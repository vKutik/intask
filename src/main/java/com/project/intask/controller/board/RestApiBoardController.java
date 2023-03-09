package com.project.intask.controller.api.v1.board;

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
@RequestMapping("/api/v1/board")
public class RestApiBoardController {

    private final BoardService boardService;

    @GetMapping
    public List<BoardDto> findAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return boardService
            .getAllBoardsByUsername(username)
            .stream()
            .map(BoardDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BoardDto getByIdBoard(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new BoardDto(boardService.getBoardByIdAndUsername(id, username));
    }

    @PostMapping
    public BoardDto createBoard(@RequestBody @Valid BoardDto boardDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return new BoardDto(
            boardService.createBoardByUsername(boardDto.toModel(), username));
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boardService.deleteBoardByIdAndUsername(id, username);
    }

    @PutMapping("/{id}")
    public BoardDto updateBoard(@PathVariable Long id,
        @RequestBody BoardDto boardDto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Board board = boardDto.toModel();
        board.setId(id);

        return new BoardDto(boardService.updateBoardByIdAndUsername(board, id, username));
    }

    @PostMapping("/{id}/task")
    public BoardDto addTaskToBoard(@PathVariable Long id,
        @RequestBody TaskDto taskDto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return new BoardDto(boardService
            .addTaskToBoardByIdAndUsername(
                id,
                taskDto.toModel(),
                username));
    }

    @PutMapping("/{boardId}/task/{taskId}")
    public TaskDto updateTaskFromBoard(@PathVariable Long boardId,
        @PathVariable Long taskId,
        @RequestBody TaskDto taskDto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return new TaskDto(boardService
            .updateTaskByBoardIdAndUsernameFromBoard(boardId, taskId, taskDto.toModel(),
                username));
    }

    @GetMapping("/{boardId}/task/{taskId}")
    public TaskDto getTaskByIdFromBoard(@PathVariable Long boardId,
        @PathVariable Long taskId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return new TaskDto(
            boardService.getTaskByIdAndUsernameFromBoard(boardId, taskId, username));
    }

    @DeleteMapping("/{boardId}/task/{taskId}")
    public void deleteTask(@PathVariable Long boardId, @PathVariable Long taskId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boardService.deleteTaskFromBoard(boardId, taskId, username);
    }

    @GetMapping("/{id}/task")
    public List<TaskDto> getAllTasksFromBoard(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return boardService
            .getAllTaskFromBoardByUsername(id, username)
            .stream()
            .map(TaskDto::new)
            .collect(Collectors.toList());
    }
}
