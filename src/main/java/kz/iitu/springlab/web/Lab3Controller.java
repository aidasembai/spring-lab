package kz.iitu.springlab.web;

import kz.iitu.springlab.config.AppProperties;
import kz.iitu.springlab.config.EnvironmentBanner;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/lab3")
public class Lab3Controller {

    private final AppProperties appProperties;
    private final EnvironmentBanner environmentBanner;
    private final Environment environment;

    public Lab3Controller(AppProperties appProperties, EnvironmentBanner environmentBanner, Environment environment) {
        this.appProperties = appProperties;
        this.environmentBanner = environmentBanner;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        return Map.ofEntries(
                Map.entry("owner", appProperties.owner()),
                Map.entry("group", appProperties.group()),
                Map.entry("mail", appProperties.mail()),
                Map.entry("rateLimit", appProperties.rateLimit()),
                Map.entry("banner", environmentBanner.describe()),
                Map.entry("activeProfiles", environment.getActiveProfiles()),
                Map.entry("serverPort", environment.getProperty("server.port", "8080"))
        );
    }
}