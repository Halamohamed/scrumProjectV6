package se.BTH.ITProjectManagement.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import se.BTH.ITProjectManagement.models.*;
import se.BTH.ITProjectManagement.repositories.SprintRepository;
import se.BTH.ITProjectManagement.repositories.TeamRepository;
import se.BTH.ITProjectManagement.services.SprintService;

import java.util.*;

@Controller
@RequestMapping("/api/sprint")
public class SprintController {
    private static final Logger log = Logger.getLogger(SprintController.class);

    @Autowired
    private SprintRepository repository;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private TeamRepository teamRepository;
    public SprintController(SprintRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/sprints", method = RequestMethod.GET)
    public String getsprints(Model model) {
        log.info("Request to fetch all sprints from the mongo database");
        List<Sprint> sprint_list = repository.findAll();
        model.addAttribute("sprints", sprint_list);
        return "sprint";
    }

    // view actualHours
    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public String printSprint(@RequestParam(value = "sprintid", required = true) String id, Model model) {
        log.info("Request to open the print sprint form page");
        model.addAttribute("sprintAttr", repository.findById(id).get());
        return "printList";
    }

   @RequestMapping(value = "/backlog", method = RequestMethod.GET)
    public String getbacklog(@RequestParam(value="sprintid") String id, Model model) {
        log.info("Request to fetch the sprint from the page");
        Sprint backlog = repository.findById(id).get();
        model.addAttribute("backlogAttr", backlog);
        model.addAttribute("sprintid", id);
        return "backlog";
    }

    // Opening the add new sprint form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addSprint(@Param(value = "name") String name, Model model) {
        log.info("Request to open the new sprint form page");
        Sprint sprint=Sprint.builder().name(name).build();
       // repository.save(sprint);
        model.addAttribute("sprintAttr", sprint);
        return "sprintform";
    }

    // Opening the edit sprint form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editSprint(@RequestParam(value="sprintid") String id, Model model) {
        log.info("Request to open the edit Sprint form page");
        model.addAttribute("sprintAttr", repository.findById(id).get());
        return "sprintform";
    }

    // view actualHours
    @RequestMapping(value = "/actualHours", method = RequestMethod.GET)
    public String editActualHours(@RequestParam(value = "sprintid", required = true) String id, Model model) {
        log.info("Request to open the edit actualHours form page");
        model.addAttribute("sprintAttr", repository.findById(id).get());
        return "actualHours";
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
            sprint.calcDelivery();
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
    public String viewTeamToSelect(@RequestParam(value = "id")String id ,Model model) {
        log.info("Request to fetch all teams from the db for custom team and select team");
        model.addAttribute("teams",teamRepository.findAll());
        model.addAttribute("sprintid", id );
        return "sprintteam";
    }

    @RequestMapping(value = "/addteam", method = RequestMethod.GET)
    public String addTeamToSprint(@RequestParam(value = "sprintid") String  sprintid,
                                  @RequestParam(value = "teamid") String teamid, Model model) {
        Sprint sprint=repository.findById(sprintid).get();
        sprint.setTeam(teamRepository.findById(teamid).get());
        repository.save(sprint);
            return "redirect:/api/sprint/edit?sprintid=" + sprintid;

    }

    @RequestMapping(value = "/sprintcharts", method = RequestMethod.GET)
    public String sprintcharts(@RequestParam(value = "sprintid", required = true) String sprintid,ModelMap modelMap) {
        modelMap.addAttribute("sprintid", sprintid);
        return "sprintcharts";
    }
    @RequestMapping(value = "/canvasjschart", method = RequestMethod.GET)
    public String canvasjschart(@RequestParam(value = "sprintid", required = true) String sprintid,ModelMap modelMap) {
        List<List<Map<Object, Object>>> canvasjsDataList = sprintService.getCanvasjsDataList(sprintid);
        modelMap.addAttribute("dataPointsList", canvasjsDataList);
        modelMap.addAttribute("sprintname", repository.findById(sprintid).get().getName());
        modelMap.addAttribute("teamname", repository.findById(sprintid).get().getTeam().getName());
        return "actualdonedaily";
    }

    @RequestMapping(value = "/canvasjschart1", method = RequestMethod.GET)
    public String canvasjschart1(@RequestParam(value = "sprintid", required = true) String sprintid,ModelMap modelMap) {
        List<List<Map<Object, Object>>> canvasjsDataList =sprintService.getCanvasjsDataList1(sprintid);
        modelMap.addAttribute("dataPointsList", canvasjsDataList);
        modelMap.addAttribute("sprintname", repository.findById(sprintid).get().getName());
        modelMap.addAttribute("teamname", repository.findById(sprintid).get().getTeam().getName());
        return "actualremaindaily";
    }
}
