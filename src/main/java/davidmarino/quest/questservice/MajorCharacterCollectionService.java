package davidmarino.quest.questservice;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import davidmarino.DynamoDbConfig;
import davidmarino.quest.questmodels.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MajorCharacterCollectionService {
    public static MajorCharacterCollection getFromDynamoDb(String majorCharacterCollectionId, ArrayList<String> characterNames) {
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbConfig.amazonDynamoDB());

        MajorCharacterCollection fullCollection = mapper.load(MajorCharacterCollection.class, majorCharacterCollectionId);
        if (fullCollection == null) return null;

        List<MajorCharacter> filteredCharacters = fullCollection.getMajorCharacters().stream()
                .filter(charac -> characterNames.contains(charac.getName()))
                .toList();

        return new MajorCharacterCollection(new ArrayList<>(filteredCharacters));
    }

    public static ArrayList<String> getAllMajorCharacterNames() {
        ArrayList<String> majorCharacterNames = new ArrayList<>();
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbConfig.amazonDynamoDB());
        List<MajorCharacterCollection> allCollections = mapper.scan(MajorCharacterCollection.class, new DynamoDBScanExpression());
        for (MajorCharacterCollection collection : allCollections) {
            if (collection.getMajorCharacters() == null) continue;
            for (MajorCharacter character : collection.getMajorCharacters()) {
                String name = character.getName();
                if (name != null && !majorCharacterNames.contains(name)) {
                    majorCharacterNames.add(name);
                }
            }
        }
        return majorCharacterNames;
    }

}
