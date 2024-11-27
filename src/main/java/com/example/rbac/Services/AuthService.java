package com.example.rbac.Services;

import com.example.rbac.Models.Role;
import com.example.rbac.Models.RoleEnum;
import com.example.rbac.Models.User;
import com.example.rbac.Repositories.RoleRepository;
import com.example.rbac.Repositories.UserRepository;
import com.example.rbac.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public String authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    public void registerUser(String username, String password, Set<RoleEnum> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));

        Set<Role> userRoles = roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found.")))
                .collect(Collectors.toSet());

        user.setRoles(userRoles);
        userRepository.save(user);
    }
}

