package mospolytech.engineering2020.fall.epprojectfall.controllers;

import java.util.Collections;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import mospolytech.engineering2020.fall.epprojectfall.domain.User;
import mospolytech.engineering2020.fall.epprojectfall.domain.enumeration.Role;
import mospolytech.engineering2020.fall.epprojectfall.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @GetMapping("/registration")
    public String registration() {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userService.findByUsername(user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "auth/registration";
        }

        user.setRole(Role.USER);
        userService.save(user);

        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }
    
    
}
