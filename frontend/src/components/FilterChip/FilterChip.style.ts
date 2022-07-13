import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const StyledFilterChip = styled.div`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 4px;

    position: relative;

    width: 92px;
    padding: 6px 16.8px 6px 12.8px;

    border-radius: 16px;
    border: 2px solid ${theme.colors.primary.base};
    background-color: ${theme.colors.white};
  `}
`;

export const FilterSpan = styled.span`
  ${({ theme }) => css`
    flex-grow: 1;

    color: ${theme.colors.primary.base};
    text-align: center;
    user-select: none;
  `}
`;

export const FilterCloseButton = styled.button`
  ${({ theme }) => css`
    display: flex;
    align-items: center;
    justify-content: center;

    position: absolute;
    right: 6px;

    border: none;
    background-color: transparent;

    svg {
      color: ${theme.colors.primary.base};
    }

    &:hover,
    &:active {
      svg {
        color: ${theme.colors.primary.light};
      }
    }
  `}
`;
