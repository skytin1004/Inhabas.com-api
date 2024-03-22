package com.inhabas.api.domain.myInfo.repository;

import static com.inhabas.api.domain.board.domain.QBaseBoard.baseBoard;
import static com.inhabas.api.domain.budget.domain.QBudgetSupportApplication.budgetSupportApplication;
import static com.inhabas.api.domain.comment.domain.QComment.comment;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import com.inhabas.api.domain.budget.domain.BudgetSupportApplication;
import com.inhabas.api.domain.comment.domain.Comment;
import com.inhabas.api.domain.myInfo.dto.MyBoardsDto;
import com.inhabas.api.domain.myInfo.dto.MyBudgetSupportApplicationDto;
import com.inhabas.api.domain.myInfo.dto.MyCommentsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

@RequiredArgsConstructor
public class MyInfoRepositoryImpl implements MyInfoRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<MyBoardsDto> findAllBoardsByMemberId(Long memberId) {
    return queryFactory
        .select(
            Projections.constructor(
                MyBoardsDto.class,
                baseBoard.id,
                baseBoard.menu.id,
                baseBoard.menu.name.value,
                baseBoard.title.value,
                baseBoard.dateCreated))
        .from(baseBoard)
        .where(
            baseBoard
                .writer
                .id
                .eq(memberId)
                // budgetSupportApplication은 예산신청 조회가 따로 있으므로, 게시판 조회 범주에서 제외
                .and(baseBoard.instanceOf(BudgetSupportApplication.class).not()))
        .orderBy(baseBoard.dateCreated.desc())
        .fetch();
  }

  @Override
  public List<MyCommentsDto> findAllCommentsByMemberId(Long memberId) {
    List<Comment> comments =
        queryFactory
            .selectFrom(comment)
            .where(comment.writer.id.eq(memberId))
            .orderBy(comment.dateCreated.desc())
            .fetch();

    return comments.stream()
        .map(
            comment ->
                new MyCommentsDto(
                    comment.getParentBoard().getId(),
                    comment.getParentBoard().getMenu().getId(),
                    comment.getParentBoard().getMenu().getName(),
                    comment.getContent(),
                    comment.getDateCreated()))
        .collect(Collectors.toList());
  }

  @Override
  public List<MyBudgetSupportApplicationDto> findAllBudgetSupportApplicationsByMemberId(
      Long memberId) {
    return queryFactory
        .select(
            Projections.constructor(
                MyBudgetSupportApplicationDto.class,
                budgetSupportApplication.id,
                budgetSupportApplication.status,
                budgetSupportApplication.title.value,
                budgetSupportApplication.dateCreated,
                budgetSupportApplication.dateChecked,
                budgetSupportApplication.dateDeposited))
        .from(budgetSupportApplication)
        .where(budgetSupportApplication.applicant.id.eq(memberId))
        .orderBy(budgetSupportApplication.dateCreated.desc())
        .fetch();
  }
}
