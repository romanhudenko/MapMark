package org.mapmark.util;


import lombok.RequiredArgsConstructor;
import org.mapmark.model.Role;
import org.mapmark.model.User;
import org.mapmark.repo.RoleRepository;
import org.mapmark.repo.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {


    private boolean load = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (load) return;
        Role userRole = createRole("ROLE_USER");
        Role adminRole = createRole("ROLE_ADMIN");
        Role moderatorRole = createRole("ROLE_MODERATOR");

        createDefaultUser("user", "user@mail.random", "user", userRole);
        createDefaultUser("admin", "admin@mail.random", "admin", adminRole);
        createDefaultUser("moder", "moder@mail.random", "moder", moderatorRole);

        load = true;
    }


    @Transactional
    public Role createRole(String name) {
        Role role = roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role = roleRepository.save(role);
        }
        return role;
    }


    @Transactional
    public User createDefaultUser(String name, String email, String password, Role role) {
        User user = userRepository.findByUsername(name).orElse(null);
        if (user == null) {
            user = new User();
            user.setUsername(name);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.addRole(role);

        }
        user = userRepository.save(user);

        return user;
    }


}
