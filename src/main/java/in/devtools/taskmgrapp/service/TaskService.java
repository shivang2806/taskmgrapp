package in.devtools.taskmgrapp.service;

import in.devtools.taskmgrapp.dto.TaskDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> getAllTask();
    void createTask(TaskDto taskDto);
    TaskDto getTaskById(Long Id);
    void updateTask(TaskDto taskDto);

}
