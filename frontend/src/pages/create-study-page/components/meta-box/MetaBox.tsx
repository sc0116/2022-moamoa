import { ReactNode } from 'react';

import * as S from '@create-study-page/components/meta-box/MetaBox.style';

const MetaBoxTitle = ({ className, children }: { className?: string; children: ReactNode }) => {
  return <h2 className={className}>{children}</h2>;
};

const MetaBoxContent = ({ className, children }: { className?: string; children: ReactNode }) => {
  return <div className={className}>{children}</div>;
};

const MetaBox = ({ className, children }: { className?: string; children: ReactNode }) => {
  return <S.MetaBox className={className}>{children}</S.MetaBox>;
};

export default Object.assign(MetaBox, {
  Title: MetaBoxTitle,
  Content: MetaBoxContent,
});
