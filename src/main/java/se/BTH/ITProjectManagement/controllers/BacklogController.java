package se.BTH.ITProjectManagement.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import se.BTH.ITProjectManagement.models.Sprint;
import se.BTH.ITProjectManagement.models.Team;
import se.BTH.ITProjectManagement.repositories.SprintRepository;
import se.BTH.ITProjectManagement.repositories.TeamRepository;

import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/api/backlog")
public class BacklogController {

        private static org.apache.log4j.Logger log = Logger.getLogger(BacklogController.class);

        @Autowired
        private SprintRepository repository;

        @Autowired
        private TeamRepository teamRepository;
        public BacklogController(SprintRepository repository) {
            this.repository = repository;
        }

        /*@RequestMapping(value = "/backlog", method = RequestMethod.GET)
        public String getbacklog(Model model) {
            log.debug("Request to fetch backlog for the sprint from the mongo database");
            List<Sprint> sprint_list = repository.findAll();
            model.addAttribute("sprints", sprint_list);

            return "sprint";
        }*/
    @RequestMapping(value = "/backlogs", method = RequestMethod.GET)
    public String getbacklog(Model model) {
        log.debug("Request to fetch all sprints from the mongo database");
        List<Sprint> sprint_list = repository.findAll();
        model.addAttribute("sprints", sprint_list);

        return "backlog";
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
            model.addAttribute("backlogAttr", repository.findById(id).get());
            return "sprintform";
        }

        // Deleting the specified sprint.
        @RequestMapping(value = "/delete", method = RequestMethod.GET)
        public String delete(@RequestParam(value="id") String id, Model model) {
            repository.deleteById(id);
            return "redirect:backlogs";
        }

        // Adding a new sprint or updating an existing sprint.
        @RequestMapping(value = "/save", method = RequestMethod.POST)
        public String save(@ModelAttribute("backlogAttr") Sprint sprint) {                  // needs test for edit or create
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

            return "redirect:backlogs";
        }


        //Select one team from teams
        @RequestMapping(value = "/teams", method = RequestMethod.GET)
        public String viewTeamToSelect(@RequestParam(value = "id")String id ,Model model) {
            log.debug("Request to fetch all teams from the db for custom team and select team");
            Team team = repository.findById(id).get().getTeam();
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
            model.addAttribute("sprintAttr", sprint);
            return "redirect:/api/sprint/edit?sprintid=" + sprintid;

        }
        @RequestMapping(value = "/canvasjschart", method = RequestMethod.GET)
        public String canvasjschart(ModelMap modelMap) {
            List<List<Map<Object, Object>>> canvasjsDataList =null;
            modelMap.addAttribute("dataPointsList", canvasjsDataList);
            return "chart";
        }


    }


