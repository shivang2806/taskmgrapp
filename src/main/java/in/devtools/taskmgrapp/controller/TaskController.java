package in.devtools.taskmgrapp.controller;

import in.devtools.taskmgrapp.dto.TaskDto;
import in.devtools.taskmgrapp.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/tasks")
    public String getAllTask(Model model)
    {
        List<TaskDto> tasks =  taskService.getAllTask();
        model.addAttribute("tasks", tasks);

        return "tasks/taskList";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/dashboard")
    public String getDashboardData(Model model)
    {
        List<TaskDto> tasks =  taskService.getAllTask();
        model.addAttribute("tasks", tasks);

        return "tasks/dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/task-create")
    public String createTask(Model model)
    {
        TaskDto taskDto = new TaskDto();
        model.addAttribute("task", taskDto);
        return "tasks/taskCreate";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/task-store")
    public String storeTask(@ModelAttribute("task") TaskDto taskDto, Model model)
    {
        taskService.createTask(taskDto);
        return "redirect:/tasks";
    }
}
