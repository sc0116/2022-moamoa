import { rest } from 'msw';

import communityArticlesJSON from '@mocks/community-articles.json';

import { PostCommunityArticleRequestBody } from '@api/community';

export const communityHandlers = [
  rest.get('/api/studies/:studyId/community/articles', (req, res, ctx) => {
    const size = req.url.searchParams.get('size');
    const page = req.url.searchParams.get('page');

    const sizeNum = Number(size);
    const pageNum = Number(page);
    const startIndex = pageNum * sizeNum;
    const endIndexExclusive = startIndex + sizeNum;

    const { articles } = communityArticlesJSON;
    const totalCount = articles.length;

    const currentPage = page;
    const lastPage = Math.floor((totalCount - 1) / sizeNum);

    return res(
      ctx.status(200),
      ctx.json({
        articles: articles.slice(startIndex, endIndexExclusive),
        currentPage,
        lastPage,
        totalCount,
      }),
    );
  }),
  rest.get('/api/studies/:studyId/community/articles/:articleId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: '게시글 아이디가' }));

    const numArticleId = Number(articleId);

    const article = communityArticlesJSON.articles.filter(({ id }) => id === numArticleId);

    return res(ctx.status(200), ctx.json(article[0]));
  }),
  rest.post<PostCommunityArticleRequestBody>('/api/studies/:studyId/community/articles', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가' }));

    const { title, content } = req.body;
    const newArticle = {
      id: Math.floor(Math.random() * 1000),
      author: {
        id: 2,
        username: 'yoon',
        imageUrl:
          'https://images.unsplash.com/photo-1599566150163-29194dcaad36?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80',
        profileUrl: 'https://github.com/airman5573',
      },
      title,
      content,
      createdDate: '2022-08-18',
      lastModifiedDate: '2022-08-18',
    };
    communityArticlesJSON.articles = [newArticle, ...communityArticlesJSON.articles];

    return res(ctx.status(201));
  }),
  rest.delete('/api/studies/:studyId/community/articles/:articleId', (req, res, ctx) => {
    const studyId = req.params.studyId;
    if (!studyId) return res(ctx.status(400), ctx.json({ errorMessage: '스터디 아이디가' }));

    const articleId = req.params.articleId;
    if (!articleId) return res(ctx.status(400), ctx.json({ errorMessage: 'article 아이디가' }));

    const numArticleId = Number(articleId);

    communityArticlesJSON.articles = communityArticlesJSON.articles.filter(({ id }) => id !== numArticleId);

    return res(ctx.status(201));
  }),
];