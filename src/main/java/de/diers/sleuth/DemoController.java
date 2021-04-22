package de.diers.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private static Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    DemoService demoService;

    @RequestMapping("/")
    @NewSpan("DemoController.home")
    public String home() {
        log.info("DemoController home");        
        return demoService.home("hallo");
    }

    @RequestMapping("/ping")
    @NewSpan("DemoController.ping")
    public String ping() {
        log.info("DemoController ping");        
        return "ping";
    }

}