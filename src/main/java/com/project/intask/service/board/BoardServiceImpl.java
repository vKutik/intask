package com.example.test.service.board;

import com.example.test.model.Board;
import com.example.test.model.Task;
import com.example.test.model.User;
import com.example.test.repository.BoardRepository;
import com.example.test.service.task.TaskService;
import com.example.test.service.user.UserService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    private final TaskService taskService;

    private final UserService userService;

    public BoardServiceImpl(BoardRepository boardRepository, TaskService taskService,
        UserService userService) {
        this.boardRepository = boardRepository;
        this.taskService = taskService;
        this.userService = userService;
    }


    @Override
    public Board createBoardByUsername(Board board, String username) {
        User user = userService.getByUsername(username);
        board.setUser(user);
        return boardRepository.save(board);
    }

    @Override
    public Board updateBoardById(Board board, Long id) {
        Board boardFromDb = getBoardById(id);
        boardFromDb.setName(board.getName());
        boardFromDb.setTasks(board.getTasks());
        return boardRepository.save(boardFromDb);
    }

    @Override
    public Board updateBoardByIdAndUsername(Board board, Long boardId, String username) {
        Board boardFromDb = getBoardByIdAndUsername(boardId, username);
        boardFromDb.setName(board.getName());
        boardFromDb.setTasks(board.getTasks());
        return updateBoardById(boardFromDb, boardId);
    }

    @Override
    public void deleteBoardByIdAndUsername(Long boardId, String username) {
        Board board = getBoardByIdAndUsername(boardId, username);
        boardRepository.deleteById(board.getId());
    }

    @Override
    public Board getBoardById(Long id) {
        return boardRepository.getBoardById(id)
            .orElseThrow(() -> new IllegalArgumentException("Board not found"));
    }

    @Override
    public Board getBoardByIdAndUsername(Long id, String username) {
        Board board = getBoardById(id);
        if (checkUsernameFromBoard(board, username)) {
            return board;
        }
        throw new IllegalArgumentException("Board not found");
    }

    @Override
    public List<Board> getAllBoardsByUsername(String username) {
        return boardRepository
            .findAll()
            .stream()
            .filter(b -> checkUsernameFromBoard(b, username))
            .collect(Collectors.toList());
    }

    @Override
    public Board addTaskToBoardByIdAndUsername(Long boardId, Task task, String username) {
        Board board = getBoardByIdAndUsername(boardId, username);
        Task taskCreated = taskService.create(task);
        List<Task> tasks = board.getTasks();
        tasks.add(taskCreated);
        return updateBoardById(board, board.getId());
    }

    @Override
    public Task getTaskByIdAndUsernameFromBoard(Long boardId, Long taskId, String username) {
        Board board = getBoardByIdAndUsername(boardId, username);

        Task task = board
            .getTasks()
            .stream()
            .filter(t -> Objects.equals(t.getId(), taskId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        return taskService.getById(task.getId());
    }

    @Override
    public Task updateTaskByBoardIdAndUsernameFromBoard(Long boardId, Long taskId, Task task,
        String username) {
        Task taskFromDb = getTaskByIdAndUsernameFromBoard(boardId, taskId,
            username);
        taskFromDb.setName(task.getName());
        taskFromDb.setDescription(task.getDescription());
        taskFromDb.setStatus(task.getStatus());
        return taskService.update(task, taskId);
    }

    @Override
    public void deleteTaskFromBoard(Long boardId, Long taskId, String username) {
        Task task = getTaskByIdAndUsernameFromBoard(boardId, taskId,
            username);
        taskService.deleteById(task.getId());
    }

    @Override
    public List<Task> getAllTaskFromBoardByUsername(Long boardId, String username) {
        Board board = getBoardByIdAndUsername(boardId, username);
        return board.getTasks();
    }


    private boolean checkUsernameFromBoard(Board board, String username) {
        return Objects.equals(board.getUser().getUsername(), username);
    }

}
