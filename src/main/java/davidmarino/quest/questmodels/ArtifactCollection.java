package davidmarino.quest.questmodels;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class ArtifactCollection {
    public String id;
    public ArrayList<ArtifactCategory> artifactCategories;

    public ArtifactCollection() {
        id = UUID.randomUUID().toString();
        artifactCategories = new ArrayList<>();
    }

    public ArtifactCollection(ArrayList<ArtifactCategory> artifactCategories) {
        id = UUID.randomUUID().toString();
        this.artifactCategories = artifactCategories;
    }

    @Override
    public String toString() {
        return artifactCategories.toString();
    }

}
