package org.example.auth.service;

import jakarta.transaction.Transactional;
import org.example.auth.dto.UserDto;
import org.example.auth.entity.Authority;
import org.example.auth.entity.User;
import org.example.auth.repository.UserRepository;
import org.example.auth.util.SecurityUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UserDto signup(UserDto userDto){
        if(userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null){
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .build();
        return UserDto.from(userRepository.save(user));

    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username){
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities(){
        return UserDto.from(
                SecurityUtil.getCurrentUser()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(()-> new UsernameNotFoundException("Member not found"))
        );
    }
}
