package kz.iitu.springlab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Value("${app.owner:Aida Sembai, IT1-2406IS}")
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

    // Вариант 3: Проверка на палиндром (поддерживает латиницу и кириллицу)
    @GetMapping("/palindrome")
    public Map<String, Object> palindrome(@RequestParam String text) {
        String decodedText = URLDecoder.decode(text, StandardCharsets.UTF_8);
        String cleanText = decodedText.replaceAll("\\s+", "").toLowerCase();
        boolean isPalindrome = cleanText.equals(new StringBuilder(cleanText).reverse().toString());
        return Map.of(
                "text", decodedText,
                "isPalindrome", isPalindrome
        );
    }

    public record Greeting(String message, String owner, LocalDateTime timestamp) { }
    public record Info(String owner, String javaVersion, int cpuCores) { }
}