package davidmarino.quest.questmodels;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class ArtifactSubCategory {
    public String id;
    public String subCategoryTitle;
    public ArrayList<Artifact> artifacts;

    public ArtifactSubCategory() {
        id = UUID.randomUUID().toString();
        subCategoryTitle = "";
        artifacts = new ArrayList<>();
    }

    public ArtifactSubCategory(String subCategoryTitle, ArrayList<Artifact> artifacts) {
        id = UUID.randomUUID().toString();
        this.subCategoryTitle = subCategoryTitle;
        this.artifacts = artifacts;
    }

    public ArtifactSubCategory(String subCategoryTitle) {
        id = UUID.randomUUID().toString();
        this.subCategoryTitle = subCategoryTitle;
        this.artifacts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "[subCategoryTitle=" + subCategoryTitle + ", artifacts=" + artifacts.toString() + "]";
    }
}
