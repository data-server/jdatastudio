package com.dataserver.admin.sys;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * sequence util
 * <p>
 * getNextSequence base on mongo
 */
@Component
public class SequenceService {

    public final static String SEQUENCE_COLLECTION = "_sequence";

    @Autowired
    SysService sysService;

    private void createCountersCollection(MongoCollection countersCollection, String sequenceName) {

        Document document = new Document();
        document.append("_id", sequenceName);
        document.append("seq", 0);
        countersCollection.insertOne(document);
    }

    /**
     * get next sequence
     * <p>
     * for this project backend use {entity+"_"+domain} name to keep identity
     *
     * @param sequenceName must identity for one collection
     * @return
     */
    public String getNextSequence(String sequenceName) {
        MongoCollection<Document> countersCollection = sysService.getTenantCollection(SEQUENCE_COLLECTION);
        if (countersCollection.count() == 0) {
            createCountersCollection(countersCollection, sequenceName);
        }
        Document searchQuery = new Document("_id", sequenceName);
        Document increase = new Document("seq", 1);
        Document updateQuery = new Document("$inc", increase);
        Document result = countersCollection.findOneAndUpdate(searchQuery, updateQuery, new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        return result.get("seq").toString();
    }

}
