package kz.iitu.springlab;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class SpringLab01ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void generateHashes() {
        var encoder = new BCryptPasswordEncoder();
        System.out.println("USER HASH 1: " + encoder.encode("user123"));
        System.out.println("USER HASH 2: " + encoder.encode("user123")); // запустится дважды для сравнения
        System.out.println("ADMIN HASH:  " + encoder.encode("admin123"));
    }
}