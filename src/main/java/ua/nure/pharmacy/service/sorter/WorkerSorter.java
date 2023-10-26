package ua.nure.pharmacy.service.sorter;

import org.springframework.stereotype.Component;
import ua.nure.pharmacy.entity.Worker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class WorkerSorter {
    public List<Worker> sortByNameAscending(List<Worker> workers) {
        List<Worker> sortedWorkers = new ArrayList<>(workers);
        sortedWorkers.sort(Comparator.comparing(Worker::getName));
        return sortedWorkers;
    }

    public List<Worker> sortByNameDescending(List<Worker> workers) {
        List<Worker> sortedWorkers = new ArrayList<>(workers);
        sortedWorkers.sort(Comparator.comparing(Worker::getName).reversed());
        return sortedWorkers;
    }
}
