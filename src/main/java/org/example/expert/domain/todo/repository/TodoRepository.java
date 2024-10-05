package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    // 날씨 조건으로 찾기
    @Query("SELECT t FROM Todo t WHERE t.weather = :weather")
    Page<Todo> findByWeather(@Param("weather") String weather, Pageable pageable);

    // 수정일 날짜 조건으로 찾기 - 두 날짜 사이
    @Query("SELECT t FROM Todo t WHERE t.modifiedAt BETWEEN :start AND :end")
    Page<Todo> findByModifiedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    // 수정일 날짜 조건으로 찾기 - 특정 날짜 이전
    @Query("SELECT t FROM Todo t WHERE t.modifiedAt < :end ")
    Page<Todo> findByModifiedAtBeforeThan(@Param("end") LocalDateTime end, Pageable pageable);

    // 수정일 날짜 조건으로 찾기 - 특정 날짜 이후
    @Query("SELECT t FROM Todo t WHERE t.modifiedAt > :start")
    Page<Todo> findByModifiedAtAfterThan(@Param("start") LocalDateTime start, Pageable pageable);

    // 날씨, 시작일, 끝일
    @Query("SELECT t FROM Todo t WHERE t.weather = :weather AND t.modifiedAt BETWEEN :start AND :end")
    Page<Todo> findByWeatherAndModifiedAtBetween(
            @Param("weather") String weather,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    // 날씨, 시작일
    @Query("SELECT t FROM Todo t WHERE t.weather = :weather AND t.modifiedAt > :start")
    Page<Todo> findByWeatherAndModifiedAtBefore (
            @Param("weather") String weather,
            @Param("start") LocalDateTime start,
            Pageable pageable
    );

    // 날씨, 끝일
    @Query("SELECT t FROM Todo t WHERE t.weather = :weather AND t.modifiedAt < :end")
    Page<Todo> findByWeatherAndModifiedAtAfter (
            @Param("weather") String weather,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );




}
