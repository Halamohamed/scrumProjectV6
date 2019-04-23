package se.BTH.ITProjectManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.BTH.ITProjectManagement.models.User;

@Controller
public class HomeController {
    @RequestMapping("/api/myuser")
    public String index(){
        return"index";
    }

    @RequestMapping(value="api/save", method= RequestMethod.POST)///api/myuser
    public ModelAndView save(@ModelAttribute User user){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user-data");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}