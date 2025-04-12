package ru.vldaislab.bekrenev.bankcardsmanager.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vldaislab.bekrenev.bankcardsmanager.DTO.UserDTO.UserDTO;
import ru.vldaislab.bekrenev.bankcardsmanager.services.auth.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO request) {
        authService.register(request.getEmail(), request.getPassword());
        return ResponseEntity.ok("Регистрация успешна");
    }
}
