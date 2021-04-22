package de.diers.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class DemoBackend {
    private static Logger log = LoggerFactory.getLogger(DemoBackend.class);

    @Autowired
    RestTemplate restTemplate;

    @NewSpan("DemoBackend.home")
    public void home() {
        log.info("demoBackend home");  

        //check if traceid will stay identically with new REST call
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/ping", String.class);
        log.info("demoBackend response: "+response.getBody());
    }

}
