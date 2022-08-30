import ArticleListItem from '@notice-tab/components/article-list-item/ArticleListItem';
import * as S from '@notice-tab/components/article-list/ArticleList.style';
import Pagination from '@notice-tab/components/pagination/Pagination';
import { useState } from 'react';
import { Link, useParams } from 'react-router-dom';

import { PATH } from '@constants';

import { useGetNoticeArticles } from '@api/notice';

type ArticleListProps = {
  className?: string;
};

const ArticleList: React.FC<ArticleListProps> = ({ className }) => {
  const { studyId } = useParams<{ studyId: string }>();
  const numStudyId = Number(studyId);
  const [page, setPage] = useState<number>(1);
  const { isFetching, isSuccess, isError, data } = useGetNoticeArticles(numStudyId, page);

  if (isFetching) {
    return <div>Loading...</div>;
  }

  if (isError || !isSuccess) {
    return <div>에러가 발생했습니다</div>;
  }

  const { articles, lastPage, currentPage } = data;

  if (articles.length === 0) {
    return <div>게시글이 없습니다</div>;
  }

  return (
    <S.Container className={className}>
      <S.ArticleList>
        {articles.map(article => (
          <Link key={article.id} to={PATH.NOTICE_ARTICLE(studyId, article.id)}>
            <ArticleListItem key={article.id} {...article}></ArticleListItem>
          </Link>
        ))}
      </S.ArticleList>
      <Pagination
        count={lastPage - 1}
        defaultPage={currentPage}
        onNumberButtonClick={num => {
          setPage(num);
        }}
      />
    </S.Container>
  );
};

export default ArticleList;