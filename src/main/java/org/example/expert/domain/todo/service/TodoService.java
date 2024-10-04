package org.example.expert.domain.todo.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.client.WeatherClient;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.dto.request.TodoSaveRequest;
import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSaveResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final WeatherClient weatherClient;

    @Transactional
    public TodoSaveResponse saveTodo(AuthUser authUser, TodoSaveRequest todoSaveRequest) {
        User user = User.fromAuthUser(authUser);

        String weather = weatherClient.getTodayWeather();

        Todo newTodo = new Todo(
                todoSaveRequest.getTitle(),
                todoSaveRequest.getContents(),
                weather,
                user
        );
        Todo savedTodo = todoRepository.save(newTodo);

        return new TodoSaveResponse(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContents(),
                weather,
                new UserResponse(user.getId(), user.getEmail(), user.getNickName())
        );
    }

    @Transactional(readOnly = true)
    public Page<TodoResponse> getTodos(int page, int size, TodoSearchRequest todoSearchRequest) {

        Pageable pageable = PageRequest.of(page - 1, size);

        // 가능한 검색 조건들
        // 날씨 유무 * 시작일 유무 * 끝일 유무 = 2^3 = 8
        Page<Todo> todos = new PageImpl<>(new ArrayList<>());

        // 요청에 날씨가 있고
        if(!todoSearchRequest.getWeather().isBlank()){
            // 시작일이 있고
            if(todoSearchRequest.getStart() != null){
                // 끝일이 있고
                if(todoSearchRequest.getEnd() != null){
                    Page<Todo> todos = todoRepository.findByWeatherAndModifiedAtBetween(
                            todoSearchRequest.getWeather(),
                            todoSearchRequest.getStart(),
                            todoSearchRequest.getEnd(),
                            pageable
                    );
                }
                // 끝일이 없고
                if(todoSearchRequest.getEnd() == null){
                    Page<Todo> todos = todoRepository.findByWeatherAndModifiedAtBefore(
                            todoSearchRequest.getWeather(),
                            todoSearchRequest.getStart(),
                            pageable
                    );
                }
            }
            // 시작일이 없고
            if(todoSearchRequest.getStart() == null){
                // 끝일이 있고
                if(todoSearchRequest.getEnd() != null){
                    Page<Todo> todos = todoRepository.findByWeatherAndModifiedAtAfter(
                            todoSearchRequest.getWeather(),
                            todoSearchRequest.getEnd(),
                            pageable
                    );
                }
                // 끝일이 없고
                if(todoSearchRequest.getEnd() == null){
                    Page<Todo> todos = todoRepository.findByWeather(
                            todoSearchRequest.getWeather(),
                            pageable
                    );
                }
            }
        }
        // 요청에 날씨가 없고
        if(todoSearchRequest.getWeather().isBlank()){
            // 시작일이 있고
            if(todoSearchRequest.getStart() != null){
                // 끝일이 있고
                if(todoSearchRequest.getEnd() != null){
                    Page<Todo> todos = todoRepository.findByModifiedAtBetween(
                            todoSearchRequest.getStart(),
                            todoSearchRequest.getEnd(),
                            pageable
                    );
                }
                // 끝일이 없고
                if(todoSearchRequest.getEnd() == null){
                    Page<Todo> todos = todoRepository.findByModifiedAtAfterThan(
                            todoSearchRequest.getStart(),
                            pageable
                    );
                }
            }
            // 시작일이 없고
            if(todoSearchRequest.getStart() == null){
                // 끝일이 있고
                if(todoSearchRequest.getEnd() != null){
                    Page<Todo> todos = todoRepository.findByModifiedAtBeforeThan(
                            todoSearchRequest.getEnd(),
                            pageable
                    );
                }
                // 끝일이 없고
                if(todoSearchRequest.getEnd() == null){
                    Page<Todo> todos = todoRepository.findAllByOrderByModifiedAtDesc(pageable);
                }
            }
        }

        return todos.map(todo -> new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(todo.getUser().getId(), todo.getUser().getEmail(), todo.getUser().getNickName()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        ));
    }

    @Transactional(readOnly = true)
    public TodoResponse getTodo(long todoId) {
        Todo todo = todoRepository.findByIdWithUser(todoId)
                .orElseThrow(() -> new InvalidRequestException("Todo not found"));

        User user = todo.getUser();

        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getWeather(),
                new UserResponse(user.getId(), user.getEmail(), user.getNickName()),
                todo.getCreatedAt(),
                todo.getModifiedAt()
        );
    }
}
