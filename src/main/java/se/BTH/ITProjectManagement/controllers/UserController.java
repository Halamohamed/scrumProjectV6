package se.BTH.ITProjectManagement.controllers;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.BTH.ITProjectManagement.handling.Initializer;
import se.BTH.ITProjectManagement.models.Role;
import se.BTH.ITProjectManagement.models.RoleName;
import se.BTH.ITProjectManagement.models.User;
import se.BTH.ITProjectManagement.repositories.RoleRepository;
import se.BTH.ITProjectManagement.repositories.UserRepository;
import se.BTH.ITProjectManagement.security.SecurityService;
import se.BTH.ITProjectManagement.security.UserService;
import se.BTH.ITProjectManagement.validator.UserValidator;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
//@RequestMapping("/api/user")
public class UserController {

    private static org.apache.log4j.Logger log = Logger.getLogger(UserController.class);

    @Autowired
    private UserRepository repository;
//    @Autowired
//    private TeamRepository teamRepository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    String timeData;

    // Displaying the initial users list.

    //@ResourceNotFoundException.Exceptions
    @RequestMapping(value = "/api/user/users", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String getUsers(Model model, Principal currentuser) {
        log.debug("Request to fetch all users from the mongo database"+currentuser.getName());
        List<User> user_list = repository.findAll();
        model.addAttribute("users", user_list);
         timeData = " username "+ currentuser.getName() + " get users  at time "+ LocalDateTime.now();
        Initializer.saveTimeData(timeData);
        return "user";
    }
    /*//@ResourceNotFoundException.Exceptions
    @RequestMapping(value = "/api/user/userlist", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public String userlist(Model model, Principal principal) {
        log.debug("Request to fetch all users from the mongo database"+principal.getName());
        List<User> user_list = repository.findAll();
        model.addAttribute("users", user_list);
        String timeData = " username "+ principal.getName() + " users " + getUsers(model,principal) + " time "+ LocalDateTime.now();
        data.saveTimeData(timeData);
        return "userlist";
    }*/

    // Opening the add new user form page.
    @RequestMapping(value = "/api/user/add", method = RequestMethod.GET)
    public String addUser(Model model,Principal currentuser) {
        log.debug("Request to open the new user form page");
       List<Role> roles= new ArrayList<>();
        roles.add(Role.builder().name(RoleName.ROLE_USER).build());
        User user=User.builder().roles(roles).active(true).build();
      //  repository.save(user);
        model.addAttribute("userAttr", user);
        timeData = " username "+ currentuser.getName() + " added user at time "+ LocalDateTime.now();
        Initializer.saveTimeData(timeData);
        return "userform";
    }

    // Opening the edit user form page.
    @RequestMapping(value = "/api/user/edit", method = RequestMethod.GET)
    public String editUser(Principal user, Model model) {
        log.debug("Request to open the edit user form page");
        model.addAttribute("userAttr", repository.findByUsername(user.getName()));
         timeData = " username "+ user.getName() + " edited user at time "+ LocalDateTime.now();
        Initializer.saveTimeData(timeData);
            return "userform";

    }
    @RequestMapping(value = "/api/user/profile", method = RequestMethod.GET)
    public String profile(User user, Model model,Principal currentuser ) {
        log.debug("Request to open the edit user form page");
        model.addAttribute("userAttr",user);
         timeData = " username "+ currentuser.getName() + " edit profile at time "+ LocalDateTime.now();
        Initializer.saveTimeData(timeData);
        return "profile";
    }
    // Deleting the specified user.
    @RequestMapping(value = "/api/user/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value="id") String id, Model model,Principal currentuser ) {
        User user=repository.findById(id).get();
        user.changeActive();
        repository.save(user);
        timeData = " username "+ currentuser.getName() + " deleted user at time "+ LocalDateTime.now();
       Initializer.saveTimeData(timeData);
        return "redirect:users";
    }

    @RequestMapping(value = "/api/user/admin", method = RequestMethod.GET)
    public String admin(@RequestParam(value="id", required=true) String id, Model model,Principal currentuser ) {
        User user=repository.findById(id).get();
        List<Role> roles= user.getRoles();
        roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN));
        user.setRoles(roles);
        repository.save(user);
         timeData = " username "+ currentuser.getName() + " get admin at time "+ LocalDateTime.now();
       Initializer.saveTimeData(timeData);
        return "redirect:users";
    }

    // Adding a new user or updating an existing user.
    @RequestMapping(value = "/api/user/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("userAttr") User user,Principal currentuser ) {                  // needs test for edit or create
            repository.save(user);
       timeData = " username "+ currentuser.getName() + " saved user at time "+ LocalDateTime.now();
        Initializer.saveTimeData(timeData);
            return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(Model model,Principal currentuser ) {
        List<Role> roles= new ArrayList<>();
        roles.add(Role.builder().name(RoleName.ROLE_USER).build());
        model.addAttribute("userForm", User.builder().roles(roles).active(true).build());
         timeData = " username "+ currentuser.getName() + " register at time "+ LocalDateTime.now();
       Initializer.saveTimeData(timeData);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,Principal currentuser ) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        List<Role> list= new ArrayList<>();
        list.add(Role.builder().name(RoleName.ROLE_USER).build());
        userService.save(User.builder().name(userForm.getName()).active(true).password(userForm.getPassword())
                .username(userForm.getUsername()).city(userForm.getCity()).phone(userForm.getPhone()).build());
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        timeData = " username "+ currentuser.getName() + " edit register at time "+ LocalDateTime.now();
        Initializer.saveTimeData(timeData);
        return "hello";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout, Principal currentuser) {

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
           Initializer.saveTimeData("logout time "+ LocalDateTime.now());
        }
        return "/login";
    }
    @GetMapping(value={"/","/api/", "/api/hello"})
    public String hello(Model model,Principal currentuser, @RequestParam(value="name", required=false, defaultValue="Administrator") String name) {
         timeData = " username " + currentuser.getName() + " login time " + LocalDateTime.now();
        Initializer.saveTimeData(timeData);
        model.addAttribute("name", name);
        return "hello";
    }


}