package uz.pdp.task1.todo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TodoRepositoryTest {
    public static void main(String[] args) throws IOException {
        TodoRepository todoRepository = new TodoRepositoryImpl();

        Todo todo = Todo.builder()
                .mongoId("66d41cf09e984e04b780f82f")
                .userId(1)
                .id(1)
                .title("delectus aut autem 1")
                .completed(false)
                .build();
//        todoRepository.saveTodo(todo);

//        URL url = new URL("https://jsonplaceholder.typicode.com/todos");
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<Todo> todos = objectMapper.readValue(url, new TypeReference<>() {
//        });
//        todoRepository.saveTodoAll(todos);

//        Todo todo = todoRepository.getTodo("66d41cf09e984e04b780f8f6");
//        System.out.println("todo: " + todo);

//        todoRepository.getTodoAll().forEach(System.out::println);

//        for (Todo todo : todoRepository.getTodoAllPaged(0, 200)) {
//            System.out.println("todo: " + todo);
//        }

//        System.out.println(todoRepository.deleteTodo("66d41cf09e984e04b780f8a7"));
//        System.out.println(todoRepository.updateTodo(todo));

        System.out.println(todoRepository.deleteTodoById(200));
    }
}
