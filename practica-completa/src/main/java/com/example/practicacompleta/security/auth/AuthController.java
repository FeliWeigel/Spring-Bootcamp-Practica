package com.example.practicacompleta.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> userRegister(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> userLogin(@RequestBody AuthRequest authRequest){
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        service.refreshToken(request, response);
//    }
}
