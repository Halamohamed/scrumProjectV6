package se.BTH.ITProjectManagement.controllers;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.BTH.ITProjectManagement.models.*;
import se.BTH.ITProjectManagement.repositories.SubTaskRepository;
import se.BTH.ITProjectManagement.repositories.TaskRepository;
import se.BTH.ITProjectManagement.repositories.UserRepository;


import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/subtask")
public class SubTaskController {

    private static org.apache.log4j.Logger log = Logger.getLogger(SubTaskController.class);

    @Autowired
    private SubTaskRepository repository;
    @Autowired
    private UserRepository userRepository;

    // Displaying the initial subtasks list.
    @RequestMapping(value = "/subtasks", method = RequestMethod.GET)
    public String getSubTasks(Model model) {
        log.debug("Request to fetch all subtasks from the mongo database");
        List<SubTask> subtask_list = repository.findAll();
        model.addAttribute("subtasks", subtask_list);
        return "subtask";
    }

    // Opening the add new subtask form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addSubTask(Model model) {
        log.debug("Request to open the new subtask form page");
        model.addAttribute("subtaskAttr", SubTask.builder().OEstimate(0).actualHours(new ArrayList<>()).build());
        return "subtaskform";
    }

    // Opening the edit subtask form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editSubTask(@RequestParam(value = "id", required = true) String id, Model model,
                              @RequestParam(value = "sprintid", required = true) String sprintid ,
                              @RequestParam(value = "taskid", required = true) String taskid) {
        log.debug("Request to open the edit subtask form page");
        model.addAttribute("subtaskAttr", repository.findById(id).get());
        model.addAttribute("taskid", taskid);
        model.addAttribute("sprintid", sprintid);
        return "subtaskform";
    }

    // Deleting the specified subtask.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id", required = true) String id, Model model) {
        repository.deleteById(id);
        return "redirect:subtasks";
    }

    // Adding a new subtask or updating an existing subtask.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("subtaskAttr") SubTask subtask) {
        if (!subtask.getId().equals(""))
            repository.save(subtask);
        else {
            SubTask subtask1 = SubTask.builder().name(subtask.getName()).actualHours(subtask.getActualHours()).OEstimate(subtask.getOEstimate())
                    .status(TaskStatus.PLANNED).users(subtask.getUsers()).build();
            repository.save(subtask1);
        }

        return "redirect:subtasks";
    }
    @RequestMapping(value = "/member", method = RequestMethod.GET) //must be put and add search
    public String members(@RequestParam("id") String id, Model model) {
        log.debug("Request to fetch all users from the mongo database");
        SubTask subTask=repository.findById(id).get();
        String user = userRepository.findById(id).get().getName();
        model.addAttribute("members", user);
        model.addAttribute("subtask", subTask);
        return "teammember";
    }

    @RequestMapping(value = "/member/actualhours", method = RequestMethod.GET) //must be put and add search
    public String actualHours(@RequestParam("id") String id, Model model) {
        log.debug("Request to fetch all actual hours from the mongo database");
        List<Integer> subTaskH=repository.findById(id).get().getActualHours();
        String user = repository.findById(id).get().getName();
        List<Integer> actualhour_list = repository.findByName(user).get().getActualHours();
        model.addAttribute("members", actualhour_list);
        model.addAttribute("subtask", subTaskH);
        return "teammember";
    }

}

/*

    @GetMapping("/subtasks")
    Collection<SubTask> subtasks() {
        return repository.findAll();
    }

    @GetMapping("/subtask/{id}")
    ResponseEntity<?> getSubTask(@PathVariable String id) {
        Optional<SubTask> subtask = repository.findById(id);
        return subtask.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/subtask", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SubTask> createSubTask(@Valid @RequestBody SubTask subtask) throws URISyntaxException {
        log.info("Request to create subtask: {}", subtask);
        SubTask result = repository.save(subtask);
        return ResponseEntity.created(new URI("/api/subtask/" + result.getId())).body(result);
    }

    @PutMapping("/Subtask")
    ResponseEntity<SubTask> updateTask(@Valid @RequestBody SubTask subtask) {
        log.info("Request to update subtask: {}", subtask);
        SubTask result = repository.save(subtask);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/subtask/{id}")
    public ResponseEntity<?> deleteSubTask(@PathVariable String id) {
        log.info("Request to delete subtask: {}", id);
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
*/