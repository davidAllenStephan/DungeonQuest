package davidmarino.quest.questmodels;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class ArtifactCategory {
    public String id;
    public String artifactCategoryTitle;
    public ArrayList<ArtifactSubCategory> artifactSubCategories;

    public ArtifactCategory() {
        id = UUID.randomUUID().toString();
        artifactCategoryTitle = "";
        artifactSubCategories = new ArrayList<>();
    }

    public ArtifactCategory(String artifactCategoryTitle) {
        id = UUID.randomUUID().toString();
        this.artifactCategoryTitle = artifactCategoryTitle;
        artifactSubCategories = new ArrayList<>();
    }

    public ArtifactCategory(String artifactCategoryTitle, ArrayList<ArtifactSubCategory> artifactSubCategories) {
        id = UUID.randomUUID().toString();
        this.artifactCategoryTitle = artifactCategoryTitle;
        this.artifactSubCategories = artifactSubCategories;
    }

    @Override
    public String toString() {
        return "\n[name=" + artifactCategoryTitle + ", artifactSubCategories=" + artifactSubCategories.toString() + "]";
    }
}
