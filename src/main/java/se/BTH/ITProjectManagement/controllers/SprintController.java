package se.BTH.ITProjectManagement.controllers;

import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.BTH.ITProjectManagement.models.*;
import se.BTH.ITProjectManagement.repositories.SprintRepository;
import se.BTH.ITProjectManagement.repositories.TeamRepository;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.AttributedString;
import java.util.*;

@Controller
@RequestMapping("/api/sprint")
public class SprintController {
    private static org.apache.log4j.Logger log = Logger.getLogger(SprintController.class);

    @Autowired
    private SprintRepository repository;

    @Autowired
    private TeamRepository teamRepository;
    public SprintController(SprintRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/sprints", method = RequestMethod.GET)
    public String getsprints(Model model) {
        log.debug("Request to fetch all sprints from the mongo database");
        List<Sprint> sprint_list = repository.findAll();
        model.addAttribute("sprints", sprint_list);

        return "sprint";
    }

    // Opening the add new sprint form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addSprint(@Param(value = "name") String name, Model model) {
        log.debug("Request to open the new sprint form page");
        Sprint sprint=Sprint.builder().name(name).build();
       // repository.save(sprint);
        model.addAttribute("sprintAttr", sprint);
        return "sprintform";
    }

    // Opening the edit sprint form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editSprint(@RequestParam(value="sprintid") String id, Model model) {
        log.debug("Request to open the edit Sprint form page");
        model.addAttribute("sprintAttr", repository.findById(id).get());
        return "sprintform";
    }

    // Deleting the specified sprint.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id") String id, Model model) {
        repository.deleteById(id);
        return "redirect:sprints";
    }

    // Adding a new sprint or updating an existing sprint.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("sprintAttr") Sprint sprint) {                  // needs test for edit or create
       Sprint sprint1;
        if(!sprint.getId().equals("")) {
            //sprint.calcDelivery();
            sprint.setTeam( repository.findById(sprint.getId()).get().getTeam());
            repository.save(sprint);
        }
            else {
            if (sprint.getTeam() == null) {
                sprint1 = Sprint.builder().name(sprint.getName()).daily_meeting(sprint.getDaily_meeting()).
                        startSprint(sprint.getStartSprint()).demo(sprint.getDemo()).goal(sprint.getGoal()).plannedPeriod(sprint.getPlannedPeriod())
                        .retrospective(sprint.getRetrospective()).review(sprint.getReview()).tasks(sprint.getTasks()).build();
            } else{ sprint1 = Sprint.builder().name(sprint.getName()).daily_meeting(sprint.getDaily_meeting()).
                    startSprint(sprint.getStartSprint()).demo(sprint.getDemo()).goal(sprint.getGoal()).plannedPeriod(sprint.getPlannedPeriod())
                    .retrospective(sprint.getRetrospective()).review(sprint.getReview()).team(sprint.getTeam()).tasks(sprint.getTasks()).build();
            }
            sprint1.calcDelivery();
            repository.save(sprint1);
        }

        return "redirect:sprints";
    }


    //Select one team from teams
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public String viewTeamToSelect(@RequestParam(value = "sprintid")String id ,Model model) {
        log.debug("Request to fetch all teams from the db for custom team and select team");
        Team team = repository.findById(id).get().getTeam();
        model.addAttribute("teams",teamRepository.findAll());
        model.addAttribute("sprintid", id );
        return "sprintteam";
    }
    @RequestMapping(value = "/addteam", method = RequestMethod.POST)
    public String addTeamToSprint(@RequestParam(value = "sprintid") String  sprintid,@RequestParam(value = "teamid") String teamid, Model model) {
        Sprint sprint=repository.findById(sprintid).get();
        sprint.setTeam(teamRepository.findById(teamid).get());
        repository.save(sprint);
        model.addAttribute("sprintAttr", sprint);
            return "redirect:/api/sprint/edit?sprintid=" + sprintid;

    }


}

/*
@RestController
@RequestMapping("/api")
public class SprintController {
    private final Logger log = LoggerFactory.getLogger(SprintController.class);

    @Autowired
    private SprintRepository repository;

    public SprintController(SprintRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/sprint")
    Collection<Sprint> sprints() {
        return repository.findAll();
    }

    @GetMapping("/sprint/{id}")
    ResponseEntity<?> getSprint(@PathVariable String id) {
        Optional<Sprint> sprint = repository.findById(id);
        return sprint.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(value = "/sprint", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Sprint> createSprint(@Valid @RequestBody Sprint sprint) throws URISyntaxException {
        log.info("Request to create sprint: {}", sprint);
        Sprint result = repository.save(sprint);
        return ResponseEntity.created(new URI("/api/sprint/" + result.getId())).body(result);
    }

    @PutMapping("/sprint")
    ResponseEntity<Sprint> updateTask(@Valid @RequestBody Sprint sprint) {
        log.info("Request to update sprint: {}", sprint);
        Sprint result = repository.save(sprint);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/sprint/{id}")
    public ResponseEntity<?> deletesprint(@PathVariable String id) {
        log.info("Request to delete sprint: {}", id);
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
*/