package com.order.ordermanagement.security.service;

import com.order.ordermanagement.controller.model.request.RegistrationRequest;
import com.order.ordermanagement.dao.entity.UserCredentialsEntity;
import com.order.ordermanagement.dao.entity.UserEntity;
import com.order.ordermanagement.dao.service.UserCredentialsDaoService;
import com.order.ordermanagement.dao.service.UserDaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();;
    private final UserDaoService userDaoService;
    private final UserCredentialsDaoService userCredentialsDaoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userDaoService.findByEmail(username);
        if (user.isEmpty()) {
            log.error("No email registered [email: {}]", username);
            throw new UsernameNotFoundException("No user with email found");
        }
        Optional<UserCredentialsEntity> credentials = userCredentialsDaoService.findById(user.get().getId());
        if (credentials.isEmpty()) {
            log.error("No email registered [email: {}]", username);
            throw new UsernameNotFoundException("No user with email found");
        }

        return new User(
                credentials.get().getId().toString(),
                credentials.get().getPassword(),
                List.of(new SimpleGrantedAuthority(user.get().getAuthority()))
        );
    }

    public UserDetails createNewUser(RegistrationRequest registrationRequest) {
        UserEntity userEntity = UserEntity.builder()
                .email(registrationRequest.email())
                .authority(registrationRequest.authority().name())
                .build();

        UserEntity saved = userDaoService.save(userEntity);
        UserCredentialsEntity credentials = new UserCredentialsEntity(saved.getId(), encoder.encode(registrationRequest.password()));
        userCredentialsDaoService.save(credentials);
        return new User(
                credentials.getId().toString(),
                credentials.getPassword(),
                List.of(new SimpleGrantedAuthority(registrationRequest.authority().name()))
        );
    }
}
