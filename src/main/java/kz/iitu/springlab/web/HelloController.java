package kz.iitu.springlab.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:unknown}")
    private String owner;

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(defaultValue = "world") String name) {
        return new Greeting("Hello, " + name + "!", owner, LocalDateTime.now());
    }

    @GetMapping("/info")
    public Info info() {
        return new Info(
                owner,
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors()
        );
    }

    // Individual Assignment (Variant 6)
    // GET /api/stats?numbers=1,5,3,9,2
    @GetMapping("/stats")
    public Map<String, Object> getStats(@RequestParam(required = false) String numbers) {
        if (numbers == null || numbers.trim().isEmpty()) {
            return Map.of("error", "Parameter 'numbers' is required. Example: ?numbers=1,5,3,9");
        }

        try {
            List<Double> list = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .map(Double::parseDouble)
                    .toList();

            if (list.isEmpty()) {
                return Map.of("error", "List of numbers is empty");
            }

            double min = list.stream().mapToDouble(Double::doubleValue).min().orElse(0);
            double max = list.stream().mapToDouble(Double::doubleValue).max().orElse(0);
            double avg = list.stream().mapToDouble(Double::doubleValue).average().orElse(0);

            return Map.of(
                    "numbers", list,
                    "min", min,
                    "max", max,
                    "average", avg
            );
        } catch (NumberFormatException e) {
            return Map.of("error", "Invalid number format. Use comma-separated numbers.");
        }
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }
    public record Info(String owner, String javaVersion, int cpuCores) { }
}