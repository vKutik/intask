package com.example.test.service.board;

import com.example.test.model.Board;
import com.example.test.model.Task;
import java.util.List;

public interface BoardService {

    Board createBoardByUsername(Board board, String username);

    Board updateBoardById(Board board, Long id);

    Board updateBoardByIdAndUsername(Board board, Long id, String username);

    void deleteBoardByIdAndUsername(Long id, String username);

    Board getBoardById(Long id);

    Board getBoardByIdAndUsername(Long id, String username);

    List<Board> getAllBoardsByUsername(String username);

    Board addTaskToBoardByIdAndUsername(Long boardId, Task task, String username);

    Task getTaskByIdAndUsernameFromBoard(Long boardId, Long taskId, String username);

    Task updateTaskByBoardIdAndUsernameFromBoard(Long boardId, Long taskId, Task task, String username);

    void deleteTaskFromBoard(Long boardId, Long taskId, String username);

    List<Task> getAllTaskFromBoardByUsername(Long boardId, String username);
}
