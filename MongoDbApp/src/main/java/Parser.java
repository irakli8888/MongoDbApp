import au.com.bytecode.opencsv.CSVReader;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Parser {

    MongoCollection<Document> collection;

    public Parser(MongoCollection<Document> collection) throws IOException {
        this.collection=collection;
    }
    public void parseFile(String path) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(path), ',', '"');

        List<String[]> allRows = reader.readAll();
        for (
                String[] row : allRows) {
            Document firstDocument = new Document()
                    .append("name", row[0])
                    .append("age", row[1])
                    .append("courses", row[2]);
            collection.insertOne(firstDocument);
        }
    }

}
