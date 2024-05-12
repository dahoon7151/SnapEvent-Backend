package com.example.snapEvent.member.security.OAuth2;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.dto.OAuthAttributes;
import com.example.snapEvent.member.repository.MemberRepository;
import com.example.snapEvent.member.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //로그인 진행중인 서비스를 구분하는 ID -> 여러 개의 소셜 로그인할 때 사용하는 ID
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2 로그인 진행 시 키가 되는 필드값(Primary Key) -> 구글은 기본적으로 해당 값 지원("sub")
        //그러나, 네이버, 카카오 로그인 시 필요한 값
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        //OAuth2UserSevice를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId,
                userNameAttributeName, oAuth2User.getAttributes());

        //우리의 서비스에 회원가입이나 기존 회원의 정보를 업데이트를 한다.
        String encodedPassword = passwordEncoder.encode(attributes.getEmail());
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        //만약에 DB에 등록된 이메일이 아니라면, save하여 DB에 등록(회원가입)을 진행시켜준다.
        Member member = memberRepository.findByUsername(attributes.getEmail())
                .orElse(attributes.toEntity(encodedPassword, roles));

        memberRepository.save(member);
        log.info("loadUser 메소드 실행 후 회원정보 저장");

        return new CustomUserDetail(createUserDetails(member), oAuth2User.getAttributes());
    }

    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }
}
