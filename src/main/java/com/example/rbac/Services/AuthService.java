package com.example.rbac.Services;

import com.example.rbac.Controllers.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.rbac.Models.Role;
import com.example.rbac.Models.RoleEnum;
import com.example.rbac.Models.User;
import com.example.rbac.Repositories.RoleRepository;
import com.example.rbac.Repositories.UserRepository;
import com.example.rbac.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    // Method to authenticate a user and return a JWT token
    public String authenticateUser(String username, String password) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            // Set the authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Return the JWT token
            return jwtUtils.generateJwtToken(authentication);
        } catch (Exception ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    // Method to register a user with roles
    public void registerUser(String username, String password, Set<RoleEnum> roles) {
        // Create a new user and set username and password
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));  // Encode the password

        // Map roles from the RoleEnum to the Role model
        Set<Role> userRoles = roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found.")))
                .collect(Collectors.toSet());

        // Set roles to the user
        user.setRoles(userRoles);

        // Save the user to the repository
        userRepository.save(user);
    }
}
