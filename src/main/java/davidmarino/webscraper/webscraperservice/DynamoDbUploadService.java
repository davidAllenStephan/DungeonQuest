package davidmarino.webscraper.webscraperservice;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamoDbUploadService {

    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    public DynamoDbUploadService(AmazonDynamoDB amazonDynamoDB) {
        this.dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
    }

    public <T> void uploadToDynamoDb(T item) {
        dynamoDBMapper.save(item);
    }
}
