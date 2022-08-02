package com.woowacourse.moamoa.study.query;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.member.domain.Member;
import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.study.domain.AttachedTag;
import com.woowacourse.moamoa.study.domain.AttachedTags;
import com.woowacourse.moamoa.study.domain.Details;
import com.woowacourse.moamoa.study.domain.Participant;
import com.woowacourse.moamoa.study.domain.Participants;
import com.woowacourse.moamoa.study.domain.Period;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.StudyStatus;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.tag.query.TagDao;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.jdbc.core.JdbcTemplate;

@RepositoryTest
public class StudySummaryDaoTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudySummaryDao studySummaryDao;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private EntityManager em;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Member jjanggu;
    private Member greenlawn;
    private Member dwoo;
    private Member verus;

    private Study javaStudy;
    private Study reactStudy;
    private Study jsStudy;
    private Study httpStudy;
    private Study algStudy;
    private Study linuxStudy;

    @BeforeEach
    void initDataBase() {
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (1, 'generation')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (2, 'area')");
        jdbcTemplate.update("INSERT INTO category(id, name) VALUES (3, 'subject')");

        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (1, 'Java', '자바', 3)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (2, '4기', '우테코4기', 1)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (3, 'BE', '백엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (4, 'FE', '프론트엔드', 2)");
        jdbcTemplate.update("INSERT INTO tag(id, name, description, category_id) VALUES (5, 'React', '리액트', 3)");

        jjanggu = memberRepository.save(new Member(1L, "jjanggu", "https://image", "github.com"));
        greenlawn = memberRepository.save(new Member(2L, "greenlawn", "https://image", "github.com"));
        dwoo = memberRepository.save(new Member(3L, "dwoo", "https://image", "github.com"));
        verus = memberRepository.save(new Member(4L, "verus", "https://image", "github.com"));

        javaStudy = studyRepository.save(new Study(
                new Details("Java 스터디", "자바 설명", "java thumbnail", "OPEN", StudyStatus.PREPARE, "그린론의 우당탕탕 자바 스터디입니다."),
                new Participants(3, 10, Set.of(new Participant(dwoo.getId()), new Participant(verus.getId())),
                        greenlawn.getId()),
                new Period(
                        LocalDate.of(2022, 11, 8),
                        LocalDate.of(2022, 12, 9),
                        LocalDate.of(2022, 12, 11)),
                new AttachedTags(List.of(new AttachedTag(1L), new AttachedTag(2L), new AttachedTag(3L)))));
        reactStudy = studyRepository.save(new Study(
                new Details("React 스터디", "리액트 설명", "react thumbnail", "OPEN", StudyStatus.PREPARE, "디우의 뤼액트 스터디입니다."),
                new Participants(4, 5, Set.of(new Participant(jjanggu.getId()), new Participant(greenlawn.getId()), new Participant(verus.getId())), dwoo.getId()),
                new Period(
                        LocalDate.of(2022, 11, 8),
                        LocalDate.of(2022, 12, 9),
                        LocalDate.of(2022, 12, 10)),
                new AttachedTags(List.of(new AttachedTag(2L), new AttachedTag(4L), new AttachedTag(5L)))));
        jsStudy = studyRepository.save(new Study(
                new Details("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN", StudyStatus.PREPARE, "그린론의 자바스크립트 접해보기"),
                new Participants(3, 20, Set.of(new Participant(dwoo.getId()), new Participant(verus.getId())), greenlawn.getId()),
                new Period(
                        LocalDate.of(2022, 11, 8),
                        LocalDate.of(2022, 12, 9),
                        LocalDate.of(2022, 12, 11)),
                new AttachedTags(List.of(new AttachedTag(2L), new AttachedTag(4L)))));
        httpStudy = studyRepository.save(new Study(
                new Details("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE", StudyStatus.PREPARE, "디우의 HTTP 정복하기"),
                new Participants(1, 5, Set.of(new Participant(dwoo.getId()), new Participant(verus.getId())), jjanggu.getId()),
                new Period(
                        LocalDate.of(2022, 11, 8),
                        LocalDate.of(2022, 12, 9),
                        LocalDate.of(2022, 12, 11)),
                new AttachedTags(List.of(new AttachedTag(2L), new AttachedTag(3L)))));
        algStudy = studyRepository.save(new Study(
                new Details("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE", StudyStatus.PREPARE, "알고리즘을 TDD로 풀자의 베루스입니다."),
                new Participants(3, 10, Set.of(new Participant(dwoo.getId()), new Participant(verus.getId())), greenlawn.getId()),
                new Period(
                        LocalDate.of(2022, 11, 8),
                        LocalDate.of(2022, 12, 9),
                        LocalDate.of(2022, 12, 11)),
                new AttachedTags(List.of())));
        linuxStudy = studyRepository.save(new Study(
                new Details("Linux 스터디", "리눅스 설명", "linux thumbnail", "CLOSE", StudyStatus.PREPARE, "Linux를 공부하자의 베루스입니다."),
                new Participants(3, 10, Set.of(new Participant(dwoo.getId()), new Participant(verus.getId())), greenlawn.getId()),
                new Period(
                        LocalDate.of(2022, 11, 8),
                        LocalDate.of(2022, 12, 9),
                        LocalDate.of(2022, 12, 11)),
                new AttachedTags(List.of())));
        em.flush();
        em.clear();
    }


    @DisplayName("페이징 정보를 사용해 스터디 목록 조회")
    @ParameterizedTest
    @MethodSource("providePageableAndExpect")
    public void findAllByPageable(Pageable pageable, List<Tuple> expectedTuples, boolean expectedHasNext) {
        final Slice<StudySummaryData> response = studySummaryDao.searchBy("", SearchingTags.emptyTags(), pageable);

        assertThat(response.hasNext()).isEqualTo(expectedHasNext);
        assertThat(response.getContent())
                .hasSize(expectedTuples.size())
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "recruitStatus")
                .containsExactlyElementsOf(expectedTuples);
    }

    private static Stream<Arguments> providePageableAndExpect() {
        List<Tuple> tuples = List.of(
                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE"),
                tuple("Linux 스터디", "리눅스 설명", "linux thumbnail", "CLOSE"));

        return Stream.of(
                Arguments.of(PageRequest.of(0, 3), tuples.subList(0, 3), true),
                Arguments.of(PageRequest.of(1, 2), tuples.subList(2, 4), true),
                Arguments.of(PageRequest.of(1, 3), tuples.subList(3, 6), false)
        );
    }

    @DisplayName("키워드와 함께 페이징 정보를 사용해 스터디 목록 조회")
    @Test
    public void findByTitleContaining() {
        final Slice<StudySummaryData> response = studySummaryDao
                .searchBy("java", SearchingTags.emptyTags(), PageRequest.of(0, 3));

        assertThat(response.hasNext()).isFalse();
        assertThat(response.getContent())
                .hasSize(2)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "recruitStatus")
                .containsExactly(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"));
    }

    @DisplayName("빈 키워드와 함께 페이징 정보를 사용해 스터디 목록 조회")
    @Test
    public void findByBlankTitle() {
        final Slice<StudySummaryData> response = studySummaryDao.searchBy("", SearchingTags.emptyTags(),
                PageRequest.of(0, 5));

        assertThat(response.hasNext()).isTrue();
        assertThat(response.getContent())
                .hasSize(5)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "excerpt", "thumbnail", "recruitStatus")
                .containsExactly(
                        tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                        tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                        tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN"),
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE"));
    }

    @DisplayName("한 가지 종류의 필터로 스터디 목록을 조회")
    @ParameterizedTest
    @MethodSource("provideOneKindFiltersAndExpectResult")
    void searchByOneKindFilter(SearchingTags searchingTags, List<Tuple> tuples) {
        Slice<StudySummaryData> response = studySummaryDao.searchBy("", searchingTags, PageRequest.of(0, 3));

        assertThat(response.hasNext()).isFalse();
        assertThat(response.getContent())
                .hasSize(tuples.size())
                .extracting("title", "excerpt", "thumbnail", "recruitStatus")
                .containsExactlyElementsOf(tuples);
    }

    private static Stream<Arguments> provideOneKindFiltersAndExpectResult() {
        return Stream.of(
                Arguments.of(new SearchingTags(emptyList(), emptyList(), List.of(5L)), // React
                        List.of(tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"))),
                Arguments.of(new SearchingTags(emptyList(), List.of(3L), emptyList()), // BE
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE")
                        )),
                Arguments.of(new SearchingTags(List.of(6L), emptyList(), emptyList()), List.of()), // 3기,
                Arguments.of(new SearchingTags(emptyList(), emptyList(), List.of(1L, 5L)), // Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN")
                        ))
        );
    }

    @DisplayName("다른 종류의 카테고리인 경우 OR 조건으로 조회")
    @ParameterizedTest
    @MethodSource("provideFiltersAndExpectResult")
    void searchByUnableToFoundTags(SearchingTags searchingTags, List<Tuple> tuples, boolean hasNext) {
        Slice<StudySummaryData> response = studySummaryDao.searchBy("", searchingTags, PageRequest.of(0, 3));

        assertThat(response.hasNext()).isEqualTo(hasNext);
        assertThat(response.getContent())
                .hasSize(tuples.size())
                .extracting("title", "excerpt", "thumbnail", "recruitStatus")
                .containsExactlyElementsOf(tuples);
    }

    private static Stream<Arguments> provideFiltersAndExpectResult() {
        return Stream.of(
                Arguments.of(new SearchingTags(List.of(2L), emptyList(), List.of(1L, 5L)), // 4기, Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN")
                        ),
                        false
                ),
                Arguments.of(new SearchingTags(emptyList(), List.of(3L), List.of(5L)), // BE, React
                        List.of(),
                        false),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L), List.of(1L)), // 4기, BE, Java
                        List.of(tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN")),
                        false
                ),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L, 4L), emptyList()), // 4기, FE, BE
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN"),
                                tuple("javaScript 스터디", "자바스크립트 설명", "javascript thumbnail", "OPEN")
                        ),
                        true
                ),
                Arguments.of(new SearchingTags(List.of(2L), List.of(3L, 4L), List.of(1L, 5L)),
                        // 4기, FE, BE, Java, React
                        List.of(
                                tuple("Java 스터디", "자바 설명", "java thumbnail", "OPEN"),
                                tuple("React 스터디", "리액트 설명", "react thumbnail", "OPEN")
                        ),
                        false
                )
        );
    }
}
