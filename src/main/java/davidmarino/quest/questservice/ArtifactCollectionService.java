package davidmarino.quest.questservice;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import davidmarino.DynamoDbConfig;
import davidmarino.quest.questmodels.ArtifactCategory;
import davidmarino.quest.questmodels.ArtifactCollection;
import davidmarino.quest.questmodels.ArtifactSubCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtifactCollectionService {

    public static ArtifactCollection getFromDynamoDb(String artifactCollectionId, ArrayList<String> subCategoryTitles) {
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbConfig.amazonDynamoDB());

        ArtifactCollection fullCollection = mapper.load(ArtifactCollection.class, artifactCollectionId);
        if (fullCollection == null) return null;

        List<ArtifactCategory> filteredCategories = fullCollection.getArtifactCategories().stream()
                .map(cat -> {
                    List<ArtifactSubCategory> filteredSubCategories = cat.getArtifactSubCategories().stream()
                            .filter(sub -> subCategoryTitles.contains(sub.getSubCategoryTitle()))
                            .toList();

                    if (!filteredSubCategories.isEmpty()) {
                        return new ArtifactCategory(cat.getArtifactCategoryTitle(), new ArrayList<>(filteredSubCategories));
                    } else {
                        return null;
                    }
                })
                .filter(cat -> cat != null)
                .toList();

        return new ArtifactCollection(new ArrayList<>(filteredCategories));
    }

    public static ArrayList<String> getAllSubCategoryTitles() {
        ArrayList<String> subCategoryTitles = new ArrayList<>();
        DynamoDbConfig dynamoDbConfig = new DynamoDbConfig();
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDbConfig.amazonDynamoDB());
        List<ArtifactCollection> allCollections = mapper.scan(ArtifactCollection.class, new DynamoDBScanExpression());
        for (ArtifactCollection collection : allCollections) {
            if (collection.getArtifactCategories() == null) continue;
            for (ArtifactCategory category : collection.getArtifactCategories()) {
                if (category.getArtifactSubCategories() == null) continue;
                for (ArtifactSubCategory subCategory : category.getArtifactSubCategories()) {
                    String title = subCategory.getSubCategoryTitle();
                    if (title != null && !subCategoryTitles.contains(title)) {
                        subCategoryTitles.add(title);
                    }
                }
            }
        }
        return subCategoryTitles;
    }

}
