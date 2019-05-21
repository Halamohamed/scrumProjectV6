package se.BTH.ITProjectManagement.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/")
public class TestController {
    private final Logger log = LoggerFactory.getLogger(TestController.class);
    @GetMapping(value={"send"})
    public String sendAccount(Model model, @RequestParam(value="value", required=false, defaultValue="personal") String value) {

        model.addAttribute("acount", value );
        return "acount";
    }

    @GetMapping(value={"get"})
    public String getParam(@RequestParam(value = "acount", required = true) String value, Model model) {

        model.addAttribute("acount", value);

        return "acount1";
    }
}
