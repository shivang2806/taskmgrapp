package in.devtools.taskmgrapp.service.impl;

import in.devtools.taskmgrapp.dto.TaskDto;
import in.devtools.taskmgrapp.entity.Task;
import in.devtools.taskmgrapp.repository.TaskRepository;
import in.devtools.taskmgrapp.service.TaskService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    @Override
    public List<TaskDto> getAllTask() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map((task)->modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
    }

    @Override
    public void createTask(TaskDto taskDto) {
        Task task = modelMapper.map(taskDto, Task.class);
        taskRepository.save(task);
    }

    @Override
    public TaskDto getTaskById(Long Id) {
        return null;
    }

    @Override
    public void updateTask(TaskDto taskDto) {

    }
}
