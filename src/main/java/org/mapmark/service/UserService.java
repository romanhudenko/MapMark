package org.mapmark.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mapmark.dto.LoginDTO;
import org.mapmark.dto.UserDTO;
import org.mapmark.model.Role;
import org.mapmark.model.User;
import org.mapmark.repo.RoleRepository;
import org.mapmark.repo.UserRepository;
import org.mapmark.security.config.AuthFacadeImpl;
import org.mapmark.util.exceptions.DataNotFoundException;
import org.mapmark.util.exceptions.UserAlreadyExistException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthFacadeImpl authFacade;


    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthFacadeImpl authFacade) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authFacade = authFacade;
    }

    public User createNewUser(UserDTO userDTO) {
        Role role = roleRepository.findByName("ROLE_USER").orElse(null);

        boolean usernameExist = userRepository.existsByUsername(userDTO.getUsername());
        if (usernameExist) throw new UserAlreadyExistException("Username already taken");

        boolean emailExist = userRepository.existsByEmail(userDTO.getEmail());
        if (emailExist) throw new UserAlreadyExistException("Email already taken");


        User user = User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        user = userRepository.save(user);
        user.addRole(role);
        user = userRepository.save(user);
        return user;
    }


    public User updateUser(UserDTO userDTO) {

        User user = User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        userRepository.save(user);


        return user;
    }


    public User removeRoleFromUser(Long userId, String role) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new DataNotFoundException("User with id " + userId + " not found");
        user.removeRole(role);

        return user;
    }

    public User promoteUserRole(Long userId, String roleName) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new DataNotFoundException("User with id " + userId + " not found");

        Role role = roleRepository.findByName(roleName).orElse(null);
        if (role == null) throw new DataNotFoundException("Invalid role " + roleName);
        user.addRole(role);

        return user;
    }


    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) throw new DataNotFoundException("User " + id + " not found");
        return user;

    }


    public String getAuthenticatedUsername() {
        return authFacade.getUsername();

    }
}
