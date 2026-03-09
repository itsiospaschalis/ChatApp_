package app.chat.springapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) String name) {

        String pageContent = """
                <h1>Hello ${name}</h1>
                <li> <a href="/hello/bootcamp"> bootcamp ${name}</a></li>
                """;

        String greetingName = "Hello World";
        if (name != null) {
            greetingName = name;
        }


        return pageContent.replace(("${name}"), greetingName);
    }


    @GetMapping("/hello/bootcamp")
    public String bootcamp() {
        return "<h1>Hello Bootcamp</h1>";
    }
}
