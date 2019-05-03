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
import se.BTH.ITProjectManagement.repositories.SprintRepository;
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
import java.util.stream.IntStream;

@Controller
@RequestMapping("/api/subtask")
public class SubTaskController {

    private static org.apache.log4j.Logger log = Logger.getLogger(SubTaskController.class);

    @Autowired
    private SubTaskRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private TaskRepository taskRepository;

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
    public String addSubTask(@RequestParam(value = "taskid", required = true) String taskid,
                             @RequestParam(value = "sprintid", required = true) String sprintid,Model model) {
        log.debug("Request to open the new subtask form page");
        model.addAttribute("subtaskAttr", SubTask.builder().OEstimate(0).users(new ArrayList<>()).build());
        model.addAttribute("taskid", taskid);
        model.addAttribute("sprintid", sprintid);
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
    public String delete(@RequestParam(value = "id", required = true) String id,
                         @RequestParam(value = "taskid", required = true) String taskid,
                         @RequestParam(value = "sprintid", required = true) String sprintid ,Model model) {
        Sprint sprint = sprintRepository.findById(sprintid).get();
        List<Task> tasks = sprint.getTasks();
        Task task = taskRepository.findById(taskid).get();
        int taskindex = sprint.findTaskIndex(taskid);
        int subtaskindex = task.findSubTaskIndex(id);
        List<SubTask> subTasks = task.getSubTasks();
        subTasks.remove(subtaskindex);
        repository.deleteById(id);
        task.setSubTasks(subTasks);
        taskRepository.save(task);
        tasks.remove(taskindex);
        tasks.add(taskindex, task);
        sprint.setTasks(tasks);
        sprintRepository.save(sprint);
        return "redirect:/api/task/edit?taskid=" + taskid + "&sprintid=" + sprintid;
    }

    // Adding a new subtask or updating an existing subtask.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("subtaskAttr") SubTask subtask,
                       @RequestParam(value = "taskid", required = true) String taskid,
                       @RequestParam(value = "sprintid", required = true) String sprintid) {
        Sprint sprint = sprintRepository.findById(sprintid).get();
        int taskIndex = sprint.findTaskIndex(taskid);
        Task task = sprint.getTasks().get(taskIndex);
        if (!subtask.getId().equals("")) {
            repository.save(subtask);
            int index = task.findSubTaskIndex(subtask.getId());
            task.getSubTasks().remove(index);
            task.getSubTasks().add(index, subtask);
        }
        else {
            List<Integer> actualHours = SubTask.intiActualHoursList(sprint.getPlannedPeriod());
            SubTask subtask1 = SubTask.builder().name(subtask.getName()).actualHours(actualHours).OEstimate(subtask.getOEstimate())
                    .status(TaskStatus.PLANNED).users(subtask.getUsers()).build();
            repository.save(subtask1);
            task.getSubTasks().add(subtask1);
        }

        taskRepository.save(task);
        sprint.getTasks().remove(taskIndex);
        sprint.getTasks().add(taskIndex, task);
        sprintRepository.save(sprint);
        return "redirect:/api/task/edit?taskid=" + taskid + "&sprintid=" + sprintid;
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
    // Opening the members of team to select member for assignment subtask form page.
    @RequestMapping(value = "/selectmember", method = RequestMethod.GET)
    public String selectmember(@RequestParam(value = "id", required = true) String id,
                               @RequestParam(value = "taskid", required = true) String taskid,
                               @RequestParam(value = "sprintid", required = true) String sprintid, Model model) {
        log.debug("Request to open the members of team for add to customed subtask form page");
        Team team = sprintRepository.findById(sprintid).get().getTeam();
        model.addAttribute("teamAttr", team);
        model.addAttribute("id", id);
        model.addAttribute("taskid", taskid);
        model.addAttribute("sprintid", sprintid);
        return "subtaskteamform";
    }

    // Deleting the specified member from assignment of subtask.
    @RequestMapping(value = "/addmember", method = RequestMethod.GET)
    public String addmember(@RequestParam(value = "userid", required = true) String userid, @RequestParam(value = "id", required = true) String subtaskid,
                            @RequestParam(value = "taskid", required = true) String taskid,
                            @RequestParam(value = "sprintid", required = true) String sprintid, Model model) {
        Sprint sprint = sprintRepository.findById(sprintid).get();
        Task task = taskRepository.findById(taskid).get();
        SubTask subTask = repository.findById(subtaskid).get();
        List<User>users=subTask.getUsers();
        if (users==null)
            users=new ArrayList<>();
        if (!users.contains(userRepository.findById(userid).get())){
            users.add(userRepository.findById(userid).get());
            subTask.setUsers(users);
            repository.save(subTask);
            int taskindex = sprint.findTaskIndex(taskid);
            int subtaskindex = sprint.getTasks().get(taskindex).findSubTaskIndex(subtaskid);
            task.getSubTasks().remove(subtaskindex);
            task.getSubTasks().add(subtaskindex, subTask);
            taskRepository.save(task);
            sprint.getTasks().remove(taskindex);
            sprint.getTasks().add(taskindex, task);
            sprintRepository.save(sprint);}


        return "redirect:/api/subtask/edit?id=" + subtaskid + "&taskid=" + taskid + "&sprintid=" + sprintid;
    }

    // Deleting the specified member from assignment of subtask.
    @RequestMapping(value = "/deletemember", method = RequestMethod.GET)
    public String deletemember(@RequestParam(value = "userid", required = true) String userid, @RequestParam(value = "id", required = true) String subtaskid,
                               @RequestParam(value = "taskid", required = true) String taskid,
                               @RequestParam(value = "sprintid", required = true) String sprintid, Model model) {
        // repository.deleteById(id);
        Sprint sprint = sprintRepository.findById(sprintid).get();
        Task task = taskRepository.findById(taskid).get();
        SubTask subTask = repository.findById(subtaskid).get();
        List<User> users=subTask.getUsers();
        int index= IntStream.range(0,users.size()).filter(i->users.get(i).getId().equals(userid)).findFirst().getAsInt();
        users.remove(index);
        subTask.setUsers(users);
        repository.save(subTask);
        int taskindex = sprint.findTaskIndex(taskid);
        int subtaskindex = sprint.getTasks().get(taskindex).findSubTaskIndex(subtaskid);
        task.getSubTasks().remove(subtaskindex);
        task.getSubTasks().add(subtaskindex, subTask);
        taskRepository.save(task);
        sprint.getTasks().remove(taskindex);
        sprint.getTasks().add(taskindex, task);
        sprintRepository.save(sprint);
        return "redirect:/api/subtask/edit?id=" + subtaskid + "&taskid=" + taskid + "&sprintid=" + sprintid;
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