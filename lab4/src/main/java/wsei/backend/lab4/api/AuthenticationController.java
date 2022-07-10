package wsei.backend.lab4.api;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import wsei.backend.lab4.database.UsersRepository;
import wsei.backend.lab4.database.entities.UserEntity;
import wsei.backend.lab4.models.UserModel;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    
    private @Autowired UsersRepository usersRepository;

    @PostMapping("register")
    public Object AuthRegister(UserModel model) {
        try {
            UserEntity user = new UserEntity(model);
            usersRepository.save(user);

            return user;
        } catch (Exception e) {
            return new LinkedHashMap<String, Object>() {
                {
                    put("errorMessage", "Error");
                }
            };
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> AuthLogin(HttpServletResponse response, @RequestBody UserModel model) {
        UserEntity user = usersRepository.findByUsername(model.getUsername());
        if (user.checkPassword(model.getPassword())) {
            // Authenticate user & create session
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("logout")
    public Object AuthLogout() {
        
    }

    @GetMapping("check")
    public Object AuthCheck() {
        
    }

}
