package com.example.snapEvent.member.service;

import com.example.snapEvent.member.dto.LoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialService {
    private final InMemoryClientRegistrationRepository clientRegistrationRepository;

    public LoginDto socialLogin(String providerName, String code) {
        ClientRegistration provider = clientRegistrationRepository.findByRegistrationId(providerName);

    }
}
