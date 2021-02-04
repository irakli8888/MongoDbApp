

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {

    private static final String PATH = "mongo.csv";
    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws IOException {

        MongoClient mongoClient = new MongoClient("192.168.99.100", 8081);

        MongoDatabase database = mongoClient.getDatabase("local");

        // Создаем коллекцию
        MongoCollection<Document> collection = database.getCollection("TestSkillDemo");

        // Удалим из нее все документы
        collection.drop();

        Parser p = new Parser(collection);
        p.parseFile(PATH);


        System.out.println("количество студентов: "+collection.count());

        FindIterable<Document> doc = collection.find(Filters.gt("age","40"));
        doc.forEach((Block<? super Document>) a->{
            count.getAndIncrement();
        });
        System.out.println("количество студентов старше 40 лет: "+ count);

        Document doc2 = collection.find().sort(new BasicDBObject("age", 1)).first();
        System.out.println("имя самого молодого студента: "+ doc2.get("name"));

        Document doc3 = collection.find().sort(new BasicDBObject("age", -1)).first();
        System.out.println("список курсов самого старого студента : "+ doc3.get("courses"));
    }
}

