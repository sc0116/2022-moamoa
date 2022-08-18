import { css } from '@emotion/react';
import styled from '@emotion/styled';

const onelineEllipsis = css`
  overflow: clip;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  word-break: break-all;
`;

export const PreviewContainer = styled.div`
  position: relative;

  &:hover {
    & > div {
      visibility: visible;
      opacity: 1;
    }
  }
`;

export const PreviewImageContainer = styled.div`
  height: 140px;
  margin-bottom: 8px;
  overflow: hidden;

  border-radius: 15px;
`;

export const PreviewDomain = styled.div`
  ${({ theme }) => css`
    position: absolute;
    bottom: 62px;
    right: 8px;
    width: 110px;
    height: 30px;
    padding: 4px;

    background-color: ${theme.colors.white};
    border-radius: 15px;
    text-align: center;
    visibility: hidden;
    opacity: 0;
    transition: visibility 0.2s ease, opacity 0.2s ease;

    & > span {
      font-weight: 600;
      font-size: 14px;
    }
  `}

  ${onelineEllipsis}
`;

export const PreviewContentContainer = styled.div`
  padding: 0 8px;
`;

export const PreviewTitle = styled.p`
  margin-bottom: 8px;

  font-weight: 700;

  ${onelineEllipsis}
`;

export const PreviewDescription = styled.p`
  ${({ theme }) => css`
    margin-bottom: 8px;

    font-size: 14px;
    color: ${theme.colors.secondary.dark};
  `}

  ${onelineEllipsis}
`;