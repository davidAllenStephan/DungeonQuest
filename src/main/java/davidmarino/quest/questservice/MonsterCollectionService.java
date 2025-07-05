package davidmarino.quest.questservice;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import davidmarino.DynamoDbConfig;
import davidmarino.quest.questmodels.MonsterCategory;
import davidmarino.quest.questmodels.MonsterCollection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MonsterCollectionService {
    public static MonsterCollection getFromDynamoDb(String monsterCollectionId, List<String> categoryTitles) {
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbConfig.amazonDynamoDB());

        MonsterCollection fullCollection = mapper.load(MonsterCollection.class, monsterCollectionId);
        if (fullCollection == null) return null;

        List<MonsterCategory> filteredCategories = fullCollection.getMonsterCategories().stream()
                .filter(cat -> categoryTitles.contains(cat.getMonsterCategoryTitle()))
                .toList();

        return new MonsterCollection(new ArrayList<>(filteredCategories));
    }
}
