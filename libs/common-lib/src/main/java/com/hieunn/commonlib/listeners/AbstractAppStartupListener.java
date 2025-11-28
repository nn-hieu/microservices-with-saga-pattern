package com.hieunn.commonlib.listeners;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

public abstract class AbstractAppStartupListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();

        String[] profiles = env.getActiveProfiles();
        String profile = profiles.length > 0 ? String.join(", ", profiles) : "default";

        String datasourceUrl = env.getProperty("spring.datasource.url", "N/A");
        String datasourceUser = env.getProperty("spring.datasource.username", "N/A");
        String port = env.getProperty("server.port", "8080");

        System.out.println("=======================================================");
        System.out.println("               APPLICATION READY");
        System.out.println("-------------------------------------------------------");
        System.out.println("Active Profiles   : " + profile);
        System.out.println("Datasource URL    : " + datasourceUrl);
        System.out.println("Database User     : " + datasourceUser);
        System.out.println("Swagger UI        : http://localhost:" + port + "/swagger-ui/index.html");
        System.out.println("=======================================================");
    }
}
