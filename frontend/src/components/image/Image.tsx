import notFoundImage from '@assets/images/no-image-found.png';

import type { CssLength } from '@custom-types';

import * as S from '@components/image/Image.style';

export type ImageProps = {
  shape: 'circular' | 'rectangular';
  src?: string | null;
  alt: string;
  width?: CssLength;
  height?: CssLength;
  objectFit?: 'cover' | 'fill' | 'contain' | 'none' | 'scale-down';
};

const Image: React.FC<ImageProps> = ({
  shape = 'rectangular',
  src,
  alt,
  width = '100%',
  height = '100%',
  objectFit = 'cover',
}) => {
  const handleImageError = ({ currentTarget }: React.SyntheticEvent<HTMLImageElement>) => {
    currentTarget.src = notFoundImage;
  };

  return (
    <S.Image
      src={src ?? notFoundImage}
      alt={alt}
      onError={handleImageError}
      shape={shape}
      width={width}
      height={height}
      objectFit={objectFit}
    />
  );
};

export default Image;