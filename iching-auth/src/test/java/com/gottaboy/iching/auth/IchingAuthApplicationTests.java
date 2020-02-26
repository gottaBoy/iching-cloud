package com.gottaboy.iching.auth;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class IchingAuthApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * $2a$10$B7rKbvR7uhH3X1.6um9j3ebqifud6rrqM40iy5a8GIi2tXrrdmPQa
     * $2a$10$9JUNeZ/Nk4RPEqJIxTEV.O2DponRtfkNBHSYrujlWgyep2hUw5sma
     * @param args
     */
    public static void main(String[] args) {
        String password = "123456";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(password));
        System.out.println(encoder.encode(password));
    }
}
