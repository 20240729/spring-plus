package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    // 날씨 조건으로 찾기
    @Query("SELECT t FROM Todo t WHERE t.weather = :weather")
    Page<Todo> findByWeather(@Param("weather") String weather);

    // 수정일 날짜 조건으로 찾기 - 두 날짜 사이
    @Query("SELECT t FROM Todo t WHERE t.modifiedAt BETWEEN :start AND :end")
    Page<Todo> findByModifiedAtBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    // 수정일 날짜 조건으로 찾기 - 특정 날짜 이전
    @Query("SELECT t FROM Todo t WHERE t.modifiedAt < :end ")
    Page<Todo> findByModifiedAtBeforeThan(@Param("end") LocalDate end);

    // 수정일 날짜 조건으로 찾기 - 특정 날짜 이후
    @Query("SELECT t FROM Todo t WHERE t.modifiedAt > :start")
    Page<Todo> findByModifiedAtAfterThan(@Param("start") LocalDate start);

}
