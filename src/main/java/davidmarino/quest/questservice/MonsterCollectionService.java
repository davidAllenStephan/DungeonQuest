package davidmarino.quest.questservice;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
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

    public static ArrayList<String> getAllMonsterCategories() {
        ArrayList<String> monsterCategories = new ArrayList<>();
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbConfig.amazonDynamoDB());
        List<MonsterCollection> allCollections = mapper.scan(MonsterCollection.class, new DynamoDBScanExpression());
        for (MonsterCollection monsterCollection: allCollections) {
            if (monsterCollection.getMonsterCategories() == null) continue;
            for (MonsterCategory category: monsterCollection.getMonsterCategories()) {
                String name = category.getMonsterCategoryTitle();
                if (name != null && !monsterCategories.contains(name)) {
                    monsterCategories.add(name);
                }
            }
        }
        return monsterCategories;
    }
}
