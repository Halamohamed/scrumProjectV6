package se.BTH.ITProjectManagement.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import se.BTH.ITProjectManagement.models.Task;
import se.BTH.ITProjectManagement.repositories.TaskRepository;

import java.util.*;

@Controller
@RequestMapping("/api/task")
public class TaskController {

    private static org.apache.log4j.Logger log = Logger.getLogger(TaskController.class);

    @Autowired
    private TaskRepository repository;

    // Displaying the initial tasks list.
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public String getTasks(Model model) {
        log.debug("Request to fetch all tasks from the mongo database");
        List<Task> task_list = repository.findAll();
        model.addAttribute("tasks", task_list);
        return "task";
    }

    // Opening the add new task form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTask(Model model) {
        log.debug("Request to open the new task form page");
        model.addAttribute("taskAttr", Task.builder().storyPoints(0).build());
        return "taskform";
    }

    // Opening the edit task form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editTask(@RequestParam(value="id", required=true) String id, Model model) {
        log.debug("Request to open the edit task form page");
        model.addAttribute("taskAttr", repository.findById(id));
        return "taskform";
    }

    // Deleting the specified task.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id", required=true) String id, Model model) {
        repository.deleteById(id);
        return "redirect:tasks";
    }

    // Adding a new task or updating an existing task.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("taskAttr") Task task) {                  // needs test for edit or create
        if (task.getId()!="")
            repository.save(task);
        else {
            Task task1=Task.builder().name(task.getName()).storyPoints(task.getStoryPoints()).priority(task.getPriority())
                    .subTasks(task.getSubTasks()).build();
            repository.save(task1);
        }

        return "redirect:tasks";
    }
}
