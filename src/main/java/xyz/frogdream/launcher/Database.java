package xyz.frogdream.launcher;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class Database {
    public static boolean statusOfKey;

    public static void main(String enteredNickname) {
        statusOfKey = true;
        /*
        MongoClient mongoClient = MongoClients.create("mongodb+srv://shelahola:CokYRQx0puBkbRi4@fdltest.ibgfrb0.mongodb.net/test");
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("players");

        Document filter = new Document("nickname", enteredNickname);
        Document result = collection.find(filter).first();

        if (result != null) {
            System.out.println("Проходка найдена: " + result.toJson());
            statusOfKey = true;
        } else {
            System.out.println("Нет проходки.");
            statusOfKey = false;
        }

        mongoClient.close();

         */
    }
}
