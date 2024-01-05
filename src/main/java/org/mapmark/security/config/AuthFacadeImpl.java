package org.mapmark.security.config;

import org.mapmark.dto.LoginDTO;
import org.mapmark.model.User;
import org.mapmark.repo.UserRepository;
import org.mapmark.security.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacadeImpl implements AuthFacade {

    private final UserRepository userRepository;

    public AuthFacadeImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsername() {
        UserDetailsImpl user = (UserDetailsImpl) getAuthentication().getPrincipal();
        return user.getUsername();
    }

    public User getAuthenticatedUserEntity() {
        UserDetailsImpl user = (UserDetailsImpl) getAuthentication().getPrincipal();
        return userRepository.findById(user.getId()).orElse(null);
    }


//    public void login(LoginDTO loginDTO) {
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
//
//    }

}
