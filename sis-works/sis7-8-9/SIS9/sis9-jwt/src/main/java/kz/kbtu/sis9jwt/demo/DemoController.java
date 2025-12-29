package kz.kbtu.sis9jwt.demo;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping("/user")
    public String user(Authentication authentication) {
        return "Hello USER: " + authentication.getName();
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        return "Hello ADMIN: " + authentication.getName();
    }
}
