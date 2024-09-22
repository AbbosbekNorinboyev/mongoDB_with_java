package uz.pdp.task1.todo;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import lombok.NonNull;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TodoRepositoryImpl implements TodoRepository {
    private static final MongoClient MONGO_CLIENT = MongoClients.create("mongodb://localhost:27017/?direction=true");
    private static final MongoDatabase DB = MONGO_CLIENT.getDatabase("spring_boot_advanced_lesson2_task");
    private static final MongoCollection<Document> TODOS = DB.getCollection("todos");

    @Override
    public Todo saveTodo(@NonNull Todo todo) {
        Map map = Map.of(
                "userId", todo.getUserId(),
                "id", todo.getId(),
                "title", todo.getTitle(),
                "completed", todo.isCompleted()
        );
        Document document = new Document(map);
        InsertOneResult insertOneResult = TODOS.insertOne(document);
        if (insertOneResult.wasAcknowledged()) {
            System.out.println("InsertedId: "
                    + Objects.requireNonNull(insertOneResult.getInsertedId())
                    .asObjectId().getValue());
            return todo;
        }
        return null;
    }

    @Override
    public List<Todo> saveTodoAll(@NonNull List<Todo> todos) {
        for (Todo todo : todos) {
            saveTodo(todo);
        }
        return todos;
    }

    @Override
    public Todo getTodo(@NonNull String mongoId) {
        Bson filter = Filters.eq("_id", new ObjectId(mongoId));
        Document document = TODOS.find(filter).first();
        assert document != null;
        return new Todo(document);
    }

    @Override
    public List<Todo> getTodoAll() {
        List<Todo> todos = new ArrayList<>();
        FindIterable<Document> documents = TODOS.find();
        for (Document document : documents) {
            todos.add(new Todo(document));
        }
        return todos;
    }

    @Override
    public List<Todo> getTodoAllPaged(int size, int page) {
        List<Todo> todos = new ArrayList<>();

        Bson sortById = Sorts.descending("id");
        Bson sortByUserId = Sorts.ascending("userId");
        Bson sort = Sorts.orderBy(sortByUserId, sortById);

        Bson filter = Filters.and(
                Filters.gte("userId", 5),
                Filters.lte("userId", 7),
                Filters.gte("id", 100)
        );
        FindIterable<Document> documents = TODOS
                .find(filter)
                .sort(sort)
                .skip(page * size)
                .limit(size);
        for (Document document : documents) {
            todos.add(new Todo(document));
        }
        return todos;
    }

    @Override
    public boolean updateTodo(@NonNull Todo todo) {
        Bson filter = Filters.eq("_id", new ObjectId(todo.getMongoId()));
        Bson update = Updates.combine(
                Updates.set("title", todo.getTitle())
        );
        return TODOS.updateOne(filter, update).wasAcknowledged();
    }

    @Override
    public boolean deleteTodo(@NonNull String mongoId) {
        Bson filter = Filters.eq("_id", new ObjectId(mongoId));
        return TODOS.deleteOne(filter).wasAcknowledged();
    }

    @Override
    public boolean deleteTodoById(@NonNull Integer id) {
        Bson filter = Filters.eq("id", id);
        return TODOS.deleteOne(filter).wasAcknowledged();
    }
}
