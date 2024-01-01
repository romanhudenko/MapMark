package org.mapmark.service;

import jakarta.annotation.Nullable;
import org.mapmark.dto.UserDTO;
import org.mapmark.model.Role;
import org.mapmark.model.User;
import org.mapmark.repo.RoleRepository;
import org.mapmark.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createNewUser(UserDTO userDTO) {
        Role role = roleRepository.findByName("ROLE_USER").orElse(null);

        boolean emailExist = userRepository.existsByEmail(userDTO.getEmail());
        if (emailExist) return null;    //FIXME EMAIL ALREADY EXIST

        boolean usernameExist = userRepository.existsByUsername(userDTO.getUsername());
        if (usernameExist) return null; //FIXME USERNAME IS TAKEN

        User user = User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();

        user = userRepository.save(user); //save user in DB -> get user id
        user.addRole(role); //default ROLE_USER role
        user = userRepository.save(user); //update user role -> userId = roleId

        return user;
    }


//    public User updateUser(UserDTO userDTO) {
//
//        User user = User.builder()
//                .email(userDTO.getEmail())
//                .username(userDTO.getUsername())
//                .password(passwordEncoder.encode(userDTO.getPassword()))
//                .build();
//
//        userRepository.save(user);
//
//
//        return user;
//    }
//
//
//    public User removeRoleFromUser(Long userId, String role) {
//
//        User user = userRepository.findById(userId).orElse(null);
//        if (user == null) return null; //fixme USER NOT FOUND
//        user.removeRole(role);
//
//        return user;
//    }
//
//    public User promoteUserRole(Long userId, String roleName) {
//
//        User user = userRepository.findById(userId).orElse(null);
//        if (user == null) return null; //fixme USER NOT FOUND
//
//        Role role = roleRepository.findByName(roleName).orElse(null);
//        if (role == null) return null; //fixme INVALID ROLE
//        user.addRole(role);
//
//        return user;
//    }


    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);

    }
}
