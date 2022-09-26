import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { ButtonGroup } from '@components/button-group/ButtonGroup.style';
import { Button } from '@components/button/Button.style';

import {
  ReviewFormBody,
  ReviewFormFooter,
  ReviewFormHead,
} from '@study-room-page/tabs/review-tab-panel/components/reivew-form/ReviewForm.style';

export {
  ReviewFormBody,
  ReviewFormFooter,
  ReviewFormHead,
  Textarea,
  Username,
} from '@study-room-page/tabs/review-tab-panel/components/reivew-form/ReviewForm.style';

export const Date = styled.span`
  ${({ theme }) => css`
    font-size: 12px;
    color: ${theme.colors.secondary.dark};
  `}
`;

export const UserInfo = styled.div`
  display: flex;
  align-items: center;
  column-gap: 6px;
`;

export const AvatarLink = styled.a``;

export const UsernameContainer = styled.div`
  display: flex;
  flex-direction: column;
  row-gap: 2px;
`;

export const UsernameLink = styled.a`
  font-size: 20px;
  font-weight: 700;
`;

export const CancelButton = styled(Button)`
  ${({ theme }) => css`
    background-color: ${theme.colors.secondary.dark};
    &:hover {
      background-color: ${theme.colors.secondary.base};
    }
  `}
`;

export const ReviewEditForm = styled.form`
  ${({ theme }) => css`
    border: 1px solid ${theme.colors.secondary.dark};
    border-radius: 4px;
  `}
`;

export const ReviewEditFormHead = styled(ReviewFormHead)``;

export const ReviewEditFormBody = styled(ReviewFormBody)``;

export const ReviewEditFormFooter = styled(ReviewFormFooter)`
  button {
    border-radius: 4px;
    padding: 8px 10px;
    font-size: 12px;
  }
`;

export const ReviewEditFormFooterButtonGroup = styled(ButtonGroup)`
  column-gap: 10px;
`;
