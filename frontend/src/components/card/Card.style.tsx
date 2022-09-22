import { css } from '@emotion/react';
import styled from '@emotion/styled';

import { nLineEllipsis } from '@utils/nLineEllipsis';

import type { MakeRequired } from '@custom-types';

import { type CardContentProps, type CardHeadingProps, type CardProps } from '@components/card/Card';

type StyledCardProps = MakeRequired<
  Pick<CardProps, 'height' | 'width' | 'backgroundColor' | 'shadow' | 'gap' | 'padding'>,
  'width' | 'height' | 'backgroundColor'
>;

type StyledCardHeadingProps = Required<Pick<CardHeadingProps, 'maxLine' | 'fontSize'>>;

type StyledCardContentProps = Required<Pick<CardContentProps, 'maxLine' | 'align' | 'fontSize'>>;

export const Card = styled.div<StyledCardProps>`
  ${({ theme, width, height, backgroundColor, shadow, gap, padding }) => css`
    display: flex;
    flex-direction: column;
    ${gap && `gap: ${gap}`};

    width: ${width};
    height: ${height};
    ${padding && `padding: ${padding}`};
    overflow: hidden;

    background-color: ${backgroundColor};
    border-radius: ${theme.radius.sm};
    ${shadow && `box-shadow: 0 0 2px 1px ${theme.colors.secondary.base};`}
  `}
`;

export const CardHeading = styled.h1<StyledCardHeadingProps>`
  ${({ theme, maxLine, fontSize }) => css`
    width: 100%;
    margin-bottom: 8px;

    font-size: ${theme.fontSize[fontSize]};
    font-weight: ${theme.fontWeight.bold};
    line-height: ${theme.fontSize[fontSize]};

    ${nLineEllipsis(maxLine)};
  `}
`;

export const CardContent = styled.p<StyledCardContentProps>`
  ${({ theme, maxLine, align, fontSize }) => css`
    width: 100%;
    height: calc(${theme.fontSize[fontSize]} * ${maxLine});

    font-size: ${theme.fontSize[fontSize]};
    line-height: ${theme.fontSize[fontSize]};
    text-align: ${align};

    ${nLineEllipsis(maxLine)};
  `}
`;
