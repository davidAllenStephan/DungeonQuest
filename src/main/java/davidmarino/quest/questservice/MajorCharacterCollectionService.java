package davidmarino.quest.questservice;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import davidmarino.DynamoDbConfig;
import davidmarino.quest.questmodels.MajorCharacter;
import davidmarino.quest.questmodels.MajorCharacterCollection;
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

}
