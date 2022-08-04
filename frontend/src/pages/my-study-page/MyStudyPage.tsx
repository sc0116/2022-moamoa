import { useMemo } from 'react';

import { css } from '@emotion/react';

import type { MyStudy, StudyStatus } from '@custom-types';

import Divider from '@components/divider/Divider';
import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@my-study-page/MyStudyPage.style';
import MyStudyCardListSection from '@my-study-page/components/my-study-card-list-section/MyStudyCardListSection';
import useGetMyStudy from '@my-study-page/hooks/useGetMyStudy';

const filterStudiesByStatus = (studies: Array<MyStudy>, status: StudyStatus) => {
  return studies.filter(({ studyStatus }) => studyStatus === status);
};

const useMyStudyPage = () => {
  const myStudyQueryResult = useGetMyStudy();

  const filteredStudies: Record<string, Array<MyStudy>> = useMemo(() => {
    const studies = myStudyQueryResult.data?.studies ?? [];
    return {
      prepare: filterStudiesByStatus(studies, 'IN_PROGRESS'),
      inProgress: filterStudiesByStatus(studies, 'PREPARE'),
      done: filterStudiesByStatus(studies, 'DONE'),
    };
  }, [myStudyQueryResult.data]);

  return {
    myStudyQueryResult,
    studies: filteredStudies,
  };
};

const MyStudyPage: React.FC = () => {
  const { myStudyQueryResult, studies } = useMyStudyPage();

  const { isFetching, isError, isSuccess } = myStudyQueryResult;

  const renderStudyListSections = () => {
    if (isFetching) {
      return <div>로딩 중...</div>;
    }

    if (isError || !isSuccess) {
      return <div>내 스터디 불러오기를 실패했습니다</div>;
    }

    const mb20 = css`
      margin-bottom: 20px;
    `;

    return (
      <>
        <MyStudyCardListSection css={mb20} sectionTitle="활동 중!" studies={studies.inProgress} />
        <MyStudyCardListSection css={mb20} sectionTitle="곧 시작해요!" studies={studies.prepare} />
        <MyStudyCardListSection css={mb20} sectionTitle="종료했어요" studies={studies.done} disabled={true} />
      </>
    );
  };

  return (
    <Wrapper>
      <S.PageTitle>가입한 스터디 목록</S.PageTitle>
      <Divider />
      {renderStudyListSections()}
    </Wrapper>
  );
};

export default MyStudyPage;