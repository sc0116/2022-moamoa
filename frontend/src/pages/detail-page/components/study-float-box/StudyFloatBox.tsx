import { Link } from 'react-router-dom';

import { PATH, RECRUITMENT_STATUS } from '@constants';

import { yyyymmddTommdd } from '@utils';
import tw from '@utils/tw';

import type { StudyDetail } from '@custom-types';

import { theme } from '@styles/theme';

import { BoxButton } from '@components/button';
import Card from '@components/card/Card';
import Flex from '@components/flex/Flex';

export type StudyFloatBoxProps = Pick<
  StudyDetail,
  'enrollmentEndDate' | 'currentMemberCount' | 'maxMemberCount' | 'recruitmentStatus'
> & {
  studyId: number;
  isOwnerOrMember: boolean;
  ownerName: string;
  onRegisterButtonClick: React.MouseEventHandler<HTMLButtonElement>;
};

const StudyFloatBox: React.FC<StudyFloatBoxProps> = ({
  studyId,
  isOwnerOrMember,
  enrollmentEndDate,
  currentMemberCount,
  maxMemberCount,
  ownerName,
  recruitmentStatus,
  onRegisterButtonClick: handleRegisterButtonClick,
}) => {
  const isOpen = recruitmentStatus === RECRUITMENT_STATUS.START;

  const renderEnrollmentEndDateContent = () => {
    if (isOwnerOrMember) {
      return <span>이미 가입한 스터디입니다</span>;
    }

    if (!isOpen) {
      return <span>모집 마감</span>;
    }

    if (!enrollmentEndDate) {
      return <span>모집중</span>;
    }

    return (
      <>
        <span>{yyyymmddTommdd(enrollmentEndDate)}</span>
        <span css={tw`text-[${theme.fontSize.lg}]`}>까지 가입 가능</span>
      </>
    );
  };

  const renderButton = () => {
    if (isOwnerOrMember || !isOpen) {
      return (
        <Link to={PATH.STUDY_ROOM(studyId)}>
          <BoxButton type="button" fontSize="lg">
            스터디 방으로 이동하기
          </BoxButton>
        </Link>
      );
    }

    return (
      <BoxButton type="submit" fontSize="lg" onClick={handleRegisterButtonClick}>
        스터디 가입하기
      </BoxButton>
    );
  };

  return (
    <Card backgroundColor={theme.colors.white} gap="8px" padding="40px" shadow>
      <Card.Heading fontSize="xl">{renderEnrollmentEndDateContent()}</Card.Heading>
      <Card.Content fontSize="lg">
        <Flex justifyContent="space-between">
          <span>모집인원</span>
          <span>
            {currentMemberCount} / {maxMemberCount ?? '∞'}
          </span>
        </Flex>
      </Card.Content>
      <Card.Content fontSize="lg">
        <Flex justifyContent="space-between">
          <span>스터디장</span>
          <span>{ownerName}</span>
        </Flex>
      </Card.Content>
      {renderButton()}
    </Card>
  );
};

export default StudyFloatBox;
