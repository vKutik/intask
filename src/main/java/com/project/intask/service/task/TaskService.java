package com.project.intask.service.task;

import com.project.intask.model.Task;
import java.util.List;

public interface TaskService {

    Task create(Task task);

    Task update(Task task, Long id);

    Task getById(Long id);

    List<Task> getAll();

    void deleteById(Long id);
}
