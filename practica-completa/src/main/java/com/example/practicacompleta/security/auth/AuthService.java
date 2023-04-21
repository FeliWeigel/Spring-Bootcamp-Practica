package com.example.practicacompleta.security.auth;

import com.example.practicacompleta.entities.Role;
import com.example.practicacompleta.entities.User;
import com.example.practicacompleta.exceptions.BusyEmailException;
import com.example.practicacompleta.exceptions.InvalidPasswordException;
import com.example.practicacompleta.repository.UserRepository;
import com.example.practicacompleta.security.config.JwtService;
import com.example.practicacompleta.security.jwt.Token;
import com.example.practicacompleta.security.jwt.TokenRepository;
import com.example.practicacompleta.security.jwt.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private Boolean isValidPassword(String password){
        char passChar;
        boolean number = false, capitalLetter =  false, symbol = false;
        Pattern special = Pattern.compile ("[?!¡@¿.,´)]");
        Matcher hasSpecial = special.matcher(password);

        for(int i = 0; i<password.length(); i++){
            passChar = password.charAt(i);

            if(Character.isDigit(passChar)){
                number = true;
            }
            if (Character.isUpperCase(passChar)){
                capitalLetter = true;
            }
            if (hasSpecial.find()){
                symbol = true;
            }
        }

        if(number && capitalLetter && symbol){
            return true;
        }else{
            return false;
        }
    }

    private void saveUserToken(User user, String jwt){
        var token = Token.builder()
                .user(user)
                .token(jwt)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user){
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public ResponseEntity<Object> register(RegisterRequest request) {
        if(!userRepository.findByEmail(request.getEmail()).isEmpty()){
            System.out.println("ejecutando1...");
            return new ResponseEntity<>(new BusyEmailException("Email Alredy exists! Please try again."), HttpStatus.CONFLICT);
        }else if(request.getPassword().length() < 6){
            System.out.println("ejecutando2...");
            return new ResponseEntity<>(new InvalidPasswordException("The password must contain at least 6 digits!"), HttpStatus.BAD_REQUEST);
        }else if(!isValidPassword(request.getPassword())){
            System.out.println("ejecutando3...");
            return new ResponseEntity<>(new InvalidPasswordException("The password must contain at least one symbol, number and capital letter!"), HttpStatus.BAD_REQUEST);
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var savedUser = userRepository.save(user);
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, token);
        return new ResponseEntity<>(
                AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build(), HttpStatus.OK);

    }

    public AuthResponse authenticate(AuthRequest authRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(authRequest.getEmail())
            .orElseThrow();
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return AuthResponse.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken)
                        .build();
    }
}
