package ua.nure.pharmacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.pharmacy.entity.Worker;
import ua.nure.pharmacy.service.impl.WorkerService;

import java.util.List;

@Controller
@RequestMapping("/workers")
public class WorkerController {
    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping("/{id}")
    public String getWorkerById(@PathVariable int id, Model model) {
        Worker worker = workerService.findById(id);
        if (worker != null) {
            model.addAttribute("worker", worker);
            return "workerDetail";
        } else {
            return "workerNotFound";
        }
    }

    @GetMapping
    public String getAllWorkers(Model model) {
        List<Worker> workers = workerService.findAll();
        if(!workers.isEmpty()) {
            model.addAttribute("workers", workers);
            return "workerList";
        }
        else{
            return "index";
        }
    }

    @PostMapping
    public String addWorker(@ModelAttribute Worker worker) {
        workerService.insert(worker);
        return "redirect:/workers";
    }

    @PutMapping("/{id}")
    public String updateWorker(@PathVariable int id, @ModelAttribute Worker worker) {
        Worker existingWorker = workerService.findById(id);
        if (existingWorker != null) {
            worker.setId(id);
            workerService.update(worker);
            return "redirect:/workers";
        } else {
            return "workerNotFound";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteWorker(@PathVariable int id) {
        Worker worker = workerService.findById(id);
        if (worker != null) {
            workerService.delete(worker);
            return "redirect:/workers";
        } else {
            return "workerNotFound";
        }
    }
}

