//package test.app121.util;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.bson.Document;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoCredential;
//import com.mongodb.ServerAddress;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//
//public class MongoUtils {
//
//    public static MongoClient getMongoClient() {
//        return getMongoClient(Config.get("mongo.host"), Config.getInt("mongo.port"), Config.get("mongo.username"),
//                Config.get("mongo.database"), Config.get("mongo.password").toCharArray());
//    }
//
//    public static MongoClient getMongoClient(String host, int port, String userName, String database, char[] password) {
//        ServerAddress sa = new ServerAddress(host, port);
//        MongoCredential mc = MongoCredential.createCredential(userName, database, password);
//        MongoClientOptions mco = MongoClientOptions.builder().build();
//        return new MongoClient(sa, mc, mco);
//    }
//
//    public static MongoCollection<Document> getMongoCollection(MongoClient client, String databaseName,
//            String collectionName) {
//        MongoDatabase db = client.getDatabase(databaseName);
//        return db.getCollection(collectionName);
//    }
//
//    public static void insert(MongoCollection<Document> collection, Map<String, Object> map) {
//        collection.insertOne(new Document(map));
//    }
//
//    public static List<Document> select(MongoCollection<Document> collection, Map<String, Object> filterMap) {
//        List<Document> list = new ArrayList<Document>();
//        BasicDBObject basicDBObject = new BasicDBObject(filterMap);
//        FindIterable<Document> findIterable = collection.find(basicDBObject);
//        try (MongoCursor<Document> cursor = findIterable.iterator()) {
//            while (cursor.hasNext()) {
//                Document document = cursor.next();
//                list.add(document);
//            }
//        }
//        return list;
//    }
//}
