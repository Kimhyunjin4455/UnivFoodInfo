package org.zerock.univFood.contoller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/univFood")
@Log4j2
public class UnivFoodController {
    @GetMapping("/register")
    public void register(){

    }
}
