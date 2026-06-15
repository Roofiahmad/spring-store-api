package com.roofiahmad.springstoreapp.feature.auth;

import com.roofiahmad.springstoreapp.infra.security.config.JwtConfig;
import com.roofiahmad.springstoreapp.feature.auth.dto.JwtResponse;
import com.roofiahmad.springstoreapp.feature.auth.dto.LoginRequest;
import com.roofiahmad.springstoreapp.feature.auth.dto.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth")
public class AuthController {

    private final JwtConfig jwtConfig;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
           @Valid @RequestBody LoginRequest request,
           HttpServletResponse response
    ) {
        var loginResponse = authService.login(request);

        var cookie = new Cookie("refreshToken", loginResponse.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue("refreshToken") String token) {
        var accessToken = authService.refreshAccessToken(token);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }



    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
