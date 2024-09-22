package uz.pdp.task1.todo;

import lombok.*;
import org.bson.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Todo {
    private String mongoId;
    private Integer userId;
    private Integer id;
    private String title;
    private boolean completed;

    public Todo(Document document) {
        this.mongoId = String.valueOf(document.getObjectId("_id"));
        this.userId = document.getInteger("userId");
        this.id = document.getInteger("id");
        this.title = document.getString("title");
        this.completed = document.getBoolean("completed");
    }

//    public String getMongoId() {
//        return mongoId;
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public boolean isCompleted() {
//        return completed;
//    }
}
