package com.graphql.graphql.controller;

import com.graphql.graphql.messaging.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
@CrossOrigin(origins = "*")
public class MessageController {

    Logger logger = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        logger.atInfo().log("received" + message);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message) + "!");
    }
}
