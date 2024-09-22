package uz.pdp.task1;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskApplication {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/?directConnection=true");
        MongoDatabase db = mongoClient.getDatabase("spring_boot_advanced_lesson2_task");
        MongoCollection<Document> users = db.getCollection("users");
//        insertOne(users);
//        insertMany(users);
//        getAllUsers(users);
//        getUserById(users);
//        updateMany(users);
//        renameField(users);
//        deleteField(users);
//        addField(users);
//        deleteOne(users);
//        deleteAll(users);
    }

    private static void deleteAll(MongoCollection<Document> users) {
        Bson filter = Filters.empty();
        DeleteResult deleteResult = users.deleteMany(filter);
        if (deleteResult.wasAcknowledged()) {
            System.out.println("deletedCount: " + deleteResult.getDeletedCount());
        }
    }

    private static void deleteOne(MongoCollection<Document> users) {
        Bson filter = Filters.eq("_id", new ObjectId("66d3309fdc8b312c66e24512"));
        DeleteResult deleteResult = users.deleteOne(filter);
        if (deleteResult.wasAcknowledged()) {
            System.out.println("deletedCount: " + deleteResult.getDeletedCount());
        }
    }

    private static void addField(MongoCollection<Document> users) {
        Bson filter = Filters.empty();
        Bson update = Updates.set("createdAt", new Date());
        UpdateResult updateResult = users.updateMany(filter, update);
        System.out.println("matchedCount: " + updateResult.getMatchedCount());
        System.out.println("modifiedCount: " + updateResult.getModifiedCount());
    }

    private static void deleteField(MongoCollection<Document> users) {
        Bson filter = Filters.exists("createdAt");
        Bson update = Updates.unset("createdAt");
        UpdateResult updateResult = users.updateMany(filter, update);
        System.out.println("modifiedCount: " + updateResult.getModifiedCount());
        System.out.println("matchedCount: " + updateResult.getMatchedCount());
    }

    private static void renameField(MongoCollection<Document> users) {
        Bson filter = Filters.exists("username");
        Bson rename = Updates.rename("username", "user_name");
        UpdateResult updateResult = users.updateMany(filter, rename);
        System.out.println("modifiedCount: " + updateResult.getModifiedCount());
        System.out.println("matchedCount: " + updateResult.getMatchedCount());
    }

    private static void updateMany(MongoCollection<Document> users) {
        Bson filter = Filters.regex("user", "^U.*$");
        Bson update = Updates.set("createdAt", new Date());
        UpdateResult updateResult = users.updateMany(filter, update);
        if (updateResult.wasAcknowledged()) {
            System.out.println("matchedCount: " + updateResult.getMatchedCount());
            System.out.println("modifiedCount: " + updateResult.getModifiedCount());
        }
    }

    private static void getUserById(MongoCollection<Document> users) {
        Bson filter = Filters.eq("_id", new ObjectId("66d32d102c895a623782ec81"));
        Document document = users.find(filter).first();
        System.out.println("document: " + document);
        assert document != null;
        Document address = document.get("address", Document.class);
        System.out.println("address: " + address);
        String zipcode = address.get("zipcode", String.class);
        System.out.println("zipcode: " + zipcode);
        Document geo = address.get("geo", Document.class);
        System.out.println("geo: " + geo);
        String lat = geo.get("lat", String.class);
        System.out.println("lat: " + lat);
    }

    private static void getAllUsers(MongoCollection<Document> users) {
        FindIterable<Document> userList = users.find();
        for (Document document : userList) {
            System.out.println(document);
        }
    }

    private static void insertMany(MongoCollection<Document> users) {
        String usersString = """
                {
                    "user" : "User 1",
                    "username" : "Username 1"
                }
                """;
        Map<String, String> map = Map.of(
                "user", "User 2",
                "username", "Username 2"
        );
        List<Document> documents = List.of(
                Document.parse(usersString),
                new Document(map)
        );
        InsertManyResult insertManyResult = users.insertMany(documents);
        if (insertManyResult.wasAcknowledged()) {
            insertManyResult.getInsertedIds().forEach((key, value) -> {
                ObjectId objectId = value.asObjectId().getValue();
                System.out.println(key + " : " + objectId);
            });
        }
    }

    private static void insertOne(MongoCollection<Document> users) {
        Document post = Document.parse("""  
                {
                  "id": 2,
                  "name": "Ervin Howell",
                  "username": "Antonette",
                  "email": "Shanna@melissa.tv",
                  "address": {
                    "street": "Victor Plains",
                    "suite": "Suite 879",
                    "city": "Wisokyburgh",
                    "zipcode": "90566-7771",
                    "geo": {
                       "lat": "-43.9509",
                       "lng": "-34.4618"
                    }
                  },
                  "phone": "010-692-6593 x09125",
                  "website": "anastasia.net",
                  "company": {
                    "name": "Deckow-Crist",
                    "catchPhrase": "Proactive didactic contingency",
                    "bs": "synergize scalable supply-chains"
                  }
                }
                """);
        InsertOneResult insertOneResult = users.insertOne(post);
        if (insertOneResult.wasAcknowledged()) {
            BsonValue insertedId = insertOneResult.getInsertedId();
            assert insertedId != null;
            ObjectId objectId = insertedId.asObjectId().getValue();
            System.out.println("objectId: " + objectId);
        }
    }
}
