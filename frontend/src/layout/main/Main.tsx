import * as S from '@layout/main/Main.style';

type MainProps = {
  children: React.ReactNode;
};

const Main: React.FC<MainProps> = ({ children }) => {
  return <S.Main>{children}</S.Main>;
};

export default Main;