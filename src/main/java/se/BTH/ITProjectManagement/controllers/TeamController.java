package se.BTH.ITProjectManagement.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.BTH.ITProjectManagement.models.*;


import se.BTH.ITProjectManagement.repositories.SprintRepository;
import se.BTH.ITProjectManagement.repositories.TeamRepository;
import se.BTH.ITProjectManagement.repositories.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/team")
public class TeamController {

    private static org.apache.log4j.Logger log = Logger.getLogger(TeamController.class);

    private static Principal currentuser;
    @Autowired
    private TeamRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SprintRepository sprintRepository;


    private TimeData data;

    // Displaying the initial teams list.
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public String getTeams(Model model) {
        log.debug("Request to fetch all teams from the mongo database");
        List<Team> team_list = repository.findAll();
        model.addAttribute("teams", team_list);

        data.saveTimeData(" username "+ currentuser.getName() + " get teams " + " time "+ LocalDateTime.now());
        return "team";
    }

    // Opening the edit team form page.
    @RequestMapping(value = "/sprintteam", method = RequestMethod.GET)
    public String sprintteam(@RequestParam(value = "sprintid", required = true) String id, Model model) {
        log.debug("Request to open the edit team form page");
        Team team;
        Sprint sprint = sprintRepository.findById(id).get();
        if (sprint.getTeam() != null) {
            team = sprint.getTeam();
            List<User> member_list = team.getUsers();
            member_list.removeIf(u -> !u.isActive());
            team.setUsers(member_list);
        } else team = Team.builder().active(true).users(new ArrayList<>()).build();
        model.addAttribute("teamAttr", team);
        model.addAttribute("sprintid", id);
        String timeData = " username "+ currentuser.getName() + " get team for sprint "+ id + " time "+ LocalDateTime.now();
        data.saveTimeData(timeData);
        return "sprintteamform";
    }
    @RequestMapping(value = "/members", method = RequestMethod.GET) //must be put and add search
    public String members(@RequestParam("id") String id , Model model) {
        log.debug("Request to fetch all users from the mongo database");
        Team team= repository.findById(id).get();
        List<User> user_list= userRepository.findAll();
        model.addAttribute("members", user_list);
        model.addAttribute("team", team);
        String timeData = " username "+ currentuser.getName() + " get members for the team " +id + " time "+ LocalDateTime.now();
        data.saveTimeData(timeData);
        return "teammember";
    }

    // add member to team and redirect to team page.
    @RequestMapping(value = "/addmember", method = RequestMethod.GET)
    public String addmember(@RequestParam(value = "id", required = true) String id,
                            @RequestParam(value = "teamid", required = true) String teamid,Model model) {
        Team team=repository.findById(teamid).get();
        List<User> members=team.getUsers();
        User user = userRepository.findById(id).get();
        if(!team.isMemberExist(user)){
            team.getUsers().add(user);
            team.setUsers(members);
        }
            repository.save(team);
        String timeData = " username "+ currentuser.getName() + " add member to the team " + teamid + " time "+ LocalDateTime.now();
        data.saveTimeData(timeData);
            return "redirect:/api/team/edit?id=" + team.getId();
        }



// Opening the add new team form page.
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTeam(Model model) {
        log.debug("Request to open the new team form page");
        Team team = Team.builder().active(true).build();
        model.addAttribute("teamAttr", team);
        String timeData = " username "+ currentuser.getName() + " added new team " + team + " time "+ LocalDateTime.now();
        data.saveTimeData(timeData);
        return "teamform";
    }

    // Opening the edit team form page.
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editTeam(@RequestParam(value = "id", required = true) String id, Model model) {
        log.debug("Request to open the edit team form page");
        Team team = repository.findById(id).get();
        model.addAttribute("teamAttr", team);
        String timeData = " username "+ currentuser.getName() + " edit team " + id + " time "+ LocalDateTime.now();
        data.saveTimeData(timeData);
        return "teamform";
    }

//    // Deleting the specified user.
//    @RequestMapping(value = "/deletemember", method = RequestMethod.GET)
//    public String deletemember(@RequestParam(value = "id", required = true) String id, @ModelAttribute("teamAttr") Team team, Model model) {
//        User user = userRepository.findById(id).get();
//        user.setActive(false);
//        userRepository.save(user);
//        List<User> member_list = team.getUsers();
//       // member_list.removeIf(u -> u.isActive() == false);
//        team.setUsers(member_list);
//        return "redirect/edit?id="+team.getId();
//    }


    // Deleting the specified team.
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "id", required = true) String id, Model model) {
        Team team = repository.findById(id).get();
            team.changeActive();
            repository.save(team);
        String timeData = " username "+ currentuser.getName() + " deleted the team " + id + " time "+ LocalDateTime.now();
        data.saveTimeData(timeData);
        return "redirect:teams";
    }

    // Adding a new team or updating an existing team.
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("teamAttr") Team team) {                  // ,@RequestBody List<User> member_list
       List<User> users= new ArrayList<>();
        if (team.getId().equals("")) {
            Team team1 = Team.builder().name(team.getName()).active(true).users(users).build();
            repository.save(team1);
        } else {
            List<User> members = team.getUsers();
            members.removeIf(u -> !u.isActive());
            team.setUsers(members);
            repository.save(team);
        }
        String timeData = " username "+ currentuser.getName() + " saved team " + team + " time "+ LocalDateTime.now();
        data.saveTimeData(timeData);
        return "redirect:teams";
    }

}
