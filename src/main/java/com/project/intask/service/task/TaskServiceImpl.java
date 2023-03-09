package com.example.test.service.task;

import com.example.test.model.Task;
import com.example.test.repository.TaskRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Override
    public Task create(Task task) {
        return repository.save(task);
    }

    @Override
    public Task update(Task task, Long id) {
        Task taskFromDb = getById(id);
        taskFromDb.setName(task.getName());
        taskFromDb.setDescription(task.getDescription());
        taskFromDb.setStatus(task.getStatus());
        return taskFromDb;
    }

    @Override
    public Task getById(Long id) {
        return repository.getTaskById(id)
            .orElseThrow(() -> new IllegalArgumentException("Task not found with id : " + id));
    }

    @Override
    public List<Task> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
