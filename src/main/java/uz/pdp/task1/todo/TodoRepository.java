package uz.pdp.task1.todo;

import lombok.NonNull;

import java.util.List;

public interface TodoRepository {
    Todo saveTodo(@NonNull Todo todo);
    List<Todo> saveTodoAll(@NonNull List<Todo> todos);
    Todo getTodo(@NonNull String mongoId);
    List<Todo> getTodoAll();
    List<Todo> getTodoAllPaged(@NonNull int size, @NonNull int page);
    boolean updateTodo(@NonNull Todo todo);
    boolean deleteTodo(@NonNull String mongoId);
    boolean deleteTodoById(@NonNull Integer id);
}
