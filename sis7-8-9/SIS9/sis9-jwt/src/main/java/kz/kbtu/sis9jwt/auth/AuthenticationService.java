package kz.kbtu.sis9jwt.auth;

import kz.kbtu.sis9jwt.config.JwtService;
import kz.kbtu.sis9jwt.user.Role;
import kz.kbtu.sis9jwt.user.User;
import kz.kbtu.sis9jwt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request, boolean admin) {
        Role role = admin ? Role.ADMIN : Role.USER;

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(token);
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // проверка логина/пароля
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(token);
        return response;
    }
}
