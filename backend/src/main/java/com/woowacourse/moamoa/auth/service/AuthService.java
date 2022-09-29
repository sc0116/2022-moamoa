package com.woowacourse.moamoa.auth.service;

import com.woowacourse.moamoa.auth.infrastructure.TokenProvider;
import com.woowacourse.moamoa.auth.service.oauthclient.OAuthClient;
import com.woowacourse.moamoa.auth.service.oauthclient.response.GithubProfileResponse;
import com.woowacourse.moamoa.auth.service.response.AccessTokenResponse;
import com.woowacourse.moamoa.auth.service.response.TokenResponse;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.MemberService;
import com.woowacourse.moamoa.member.service.exception.MemberNotFoundException;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final OAuthClient oAuthClient;
    private final MemberRepository memberRepository;

    @Transactional
    public AccessTokenResponse createToken(final String code) {
        final String accessToken = oAuthClient.getAccessToken(code);
        final GithubProfileResponse githubProfileResponse = oAuthClient.getProfile(accessToken);
        final MemberResponse memberResponse = memberService.saveOrUpdate(githubProfileResponse.toMember());
        final Long memberId = memberResponse.getId();

        final TokenResponse token = tokenProvider.createToken(memberId);
        return new AccessTokenResponse(token.getAccessToken(), tokenProvider.getValidityInMilliseconds());
    }

    public AccessTokenResponse refreshToken(final Long memberId) {
        checkExistMember(memberId);

        final TokenResponse token = tokenProvider.createToken(memberId);
        return new AccessTokenResponse(token.getAccessToken(), tokenProvider.getValidityInMilliseconds());
    }

    private void checkExistMember(final Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
