package de.diers.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
    private static Logger log = LoggerFactory.getLogger(DemoService.class);

    @Autowired
    DemoBackend demoBackend;

    @NewSpan("DemoService.home")
    public String home(String input) {
        log.info("DemoService home: "+input);
        demoBackend.home();
        return "Hello World";
    }
    
}
