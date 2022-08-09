package com.woowacourse.moamoa.review.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.common.utils.DateTimeSystem;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.domain.repository.ReviewRepository;
import com.woowacourse.moamoa.review.service.ReviewService;
import com.woowacourse.moamoa.review.service.exception.UnwrittenReviewException;
import com.woowacourse.moamoa.review.service.request.EditingReviewRequest;
import com.woowacourse.moamoa.review.service.request.WriteReviewRequest;
import com.woowacourse.moamoa.review.service.response.ReviewResponse;
import com.woowacourse.moamoa.review.service.response.WriterResponse;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.StudyService;
import com.woowacourse.moamoa.study.service.request.CreatingStudyRequest;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
public class ReviewControllerTest {

    private static final MemberData 짱구 = new MemberData(1L, "jjanggu", "https://image", "github.com");
    private static final MemberData 베루스 = new MemberData(4L, "verus", "https://image", "github.com");

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ReviewRepository reviewRepository;

    private ReviewController sut;

    private Long 짱구_리뷰;

    @BeforeEach
    void setUp() {
        sut = new ReviewController(new ReviewService(reviewRepository, memberRepository, studyRepository));

        // 사용자 추가
        final Member jjanggu = memberRepository.save(toMember(짱구));
        final Member verus = memberRepository.save(toMember(베루스));

        // 스터디 생성
        StudyService studyService = new StudyService(studyRepository, memberRepository, new DateTimeSystem());

        final LocalDate startDate = LocalDate.now();
        CreatingStudyRequest javaStudyRequest = CreatingStudyRequest.builder()
                .title("java 스터디").excerpt("자바 설명").thumbnail("java image").description("자바 소개")
                .startDate(startDate)
                .build();

        Study javaStudy = studyService.createStudy(1L, javaStudyRequest);

        studyService.participateStudy(verus.getGithubId(), javaStudy.getId());

        // 리뷰 추가
        ReviewService reviewService = new ReviewService(reviewRepository, memberRepository, studyRepository);

        짱구_리뷰 = reviewService
                .writeReview(jjanggu.getGithubId(), javaStudy.getId(), new WriteReviewRequest("리뷰 내용1"));
        final Long javaReviewId4 = reviewService
                .writeReview(verus.getGithubId(), javaStudy.getId(), new WriteReviewRequest("리뷰 내용4"));

        final ReviewResponse 리뷰_내용1 = new ReviewResponse(짱구_리뷰, new WriterResponse(짱구), LocalDate.now(),
                LocalDate.now(), "리뷰 내용1");
        final ReviewResponse 리뷰_내용4 = new ReviewResponse(javaReviewId4, new WriterResponse(베루스), LocalDate.now(),
                LocalDate.now(), "리뷰 내용4");

        entityManager.flush();
        entityManager.clear();
    }

    private static Member toMember(MemberData memberData) {
        return new Member(memberData.getGithubId(), memberData.getUsername(), memberData.getImageUrl(),
                memberData.getProfileUrl());
    }

    @DisplayName("내가 작성하지 않은 리뷰를 수정할 수 없다.")
    @Test
    void notUpdate() {
        final EditingReviewRequest request = new EditingReviewRequest("수정한 리뷰 내용입니다.");

        assertThatThrownBy(() -> sut.updateReview(베루스.getGithubId(), 짱구_리뷰, request))
                .isInstanceOf(UnwrittenReviewException.class);
    }

    @DisplayName("내가 작성하지 않은 리뷰를 삭제할 수 없다.")
    @Test
    void notDelete() {
        assertThatThrownBy(() -> sut.deleteReview(베루스.getGithubId(), 짱구_리뷰))
                .isInstanceOf(UnwrittenReviewException.class);
    }
}