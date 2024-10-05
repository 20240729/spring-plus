package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TodoRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    // 원문 쿼리
    // @Query("SELECT t FROM Todo t " +
    //            "LEFT JOIN t.user " +
    //            "WHERE t.id = :todoId")
    //    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId){
        QTodo qTodo = QTodo.todo;
        QUser qUser = QUser.user;

        Todo todo = queryFactory.selectFrom(qTodo) // SELECT t FROM todo t
                .leftJoin(qTodo.user, qUser) // LEFT JOIN t.user
                .fetchJoin() // N+1 문제를 방지하기 위해 넣음.
                .where(qTodo.id.eq(todoId)) // WHERE t.id = :todoId
                .fetchOne(); // 조건을 만족하는 단일 혹은 비어있는 결과를 반환. 2개 이상이면 NonUniqueResultException 발생. 결과가 0 또는 1개일 때 사용함.

        return Optional.ofNullable(todo);
    }


}
