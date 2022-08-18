import { AxiosError } from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import tw from '@utils/tw';

import Wrapper from '@components/wrapper/Wrapper';

import * as S from '@community-tab/CommunityTabPanel.style';
import ArticleList from '@community-tab/components/article-list/ArticleList';
import Article from '@community-tab/components/article/Article';
import Edit from '@community-tab/components/edit/Edit';
import Publish from '@community-tab/components/publish/Publish';

AxiosError;

type CommunityTabPanelProps = {
  studyId: number;
};

const CommunityTabPanel: React.FC<CommunityTabPanelProps> = ({ studyId }) => {
  const { articleId } = useParams<{ articleId: string }>();
  const navigate = useNavigate();

  const lastPath = window.location.pathname.split('/').at(-1);
  const isPublishPage = lastPath === 'publish';
  const isEditPage = lastPath === 'edit';
  const isArticleDetailPage = !!(articleId && !isPublishPage && !isEditPage);
  const isListPage = !!(!articleId && !isPublishPage && !isEditPage && !isArticleDetailPage);

  const handleGoToPublishPageButtonClick = () => {
    navigate(`${PATH.COMMUNITY_PUBLISH(studyId)}`);
  };

  const renderArticleListPage = () => {
    return (
      <>
        <ArticleList />
        <div css={tw`flex justify-end`}>
          <S.Button type="button" onClick={handleGoToPublishPageButtonClick}>
            글쓰기
          </S.Button>
        </div>
      </>
    );
  };

  const render = () => {
    if (isListPage) {
      return renderArticleListPage();
    }
    if (isArticleDetailPage) {
      const numArticleId = Number(articleId);
      return <Article studyId={studyId} articleId={numArticleId} />;
    }
    if (isPublishPage) {
      return <Publish />;
    }
    if (isEditPage) {
      return <Edit />;
    }
  };

  return (
    <Wrapper>
      <S.CommunityTabPanel>
        <S.Board>
          <h1 css={tw`text-center text-30 mb-40`}>커뮤니티 게시판</h1>
          <div css={tw`min-h-[300px]`}>{render()}</div>
        </S.Board>
      </S.CommunityTabPanel>
    </Wrapper>
  );
};

export default CommunityTabPanel;