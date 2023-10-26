package ua.nure.pharmacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.nure.pharmacy.repository.impl.Task;
import ua.nure.pharmacy.repository.impl.TaskRepository;

import java.util.List;

@Controller
public class TaskController {
    TaskRepository taskRepository = new TaskRepository();

    @GetMapping("/task")
    public String task(Model model){
        List<Task> taskList = taskRepository.findByTask();
        model.addAttribute("taskList", taskList);
        return "task";
    }
}
