package davidmarino.webscraper;

import davidmarino.quest.questmodels.Artifact;
import davidmarino.quest.questmodels.ArtifactCategory;
import davidmarino.quest.questmodels.ArtifactCollection;
import davidmarino.quest.questmodels.ArtifactSubCategory;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtifactScraper {

    @Autowired
    private final QuestScraper questScraper = new QuestScraper();

    private final static Logger logger = LoggerFactory.getLogger(ArtifactScraper.class);

    public ArtifactCollection getArtifactCollection() {
        Document doc = QuestScraper.getDocument("https://en.wikipedia.org/wiki/List_of_mythological_objects");
        Element body = doc.body();
        questScraper.removeBlank(body);
        ArtifactCollection artifactCollection = new ArtifactCollection();
        Elements allElements = body.select("h2, h3, ul");
        ArtifactCategory currentCategory = null;
        ArtifactSubCategory currentSubCategory = null;
        for (Element element : allElements) {
            if (element.tagName().equals("h2")) {
                String categoryTitle = element.text().replace("[edit]", "").trim();
                currentCategory = new ArtifactCategory(categoryTitle);
                artifactCollection.artifactCategories.add(currentCategory);
                currentSubCategory = null; // reset subcategory
            } else if (element.tagName().equals("h3")) {
                if (currentCategory == null) continue;
                String subCategoryTitle = element.text().replace("[edit]", "").trim();
                currentSubCategory = new ArtifactSubCategory(subCategoryTitle);
                currentCategory.artifactSubCategories.add(currentSubCategory);
            } else if (element.tagName().equals("ul")) {
                if (currentSubCategory != null) {
                    for (Element li : element.select("li")) {
                        String name = "";
                        String description = "";
                        Element link = li.selectFirst("a");
                        if (link != null) {
                            name = link.text();
                            String fullText = li.text();
                            String textAfterName = fullText.substring(name.length()).trim();
                            if (textAfterName.startsWith(",") || textAfterName.startsWith("–") || textAfterName.startsWith("-")) {
                                textAfterName = textAfterName.substring(1).trim();
                            }
                            description = textAfterName;
                        } else {
                            name = li.text();
                            description = "";
                        }
                        Artifact artifact = new Artifact(name, description);
                        currentSubCategory.artifacts.add(artifact);
                    }
                }
            }

        }
        artifactCollection.artifactCategories.remove(0);
        for (int i = 0; i < 18; i++) {
            artifactCollection.artifactCategories.remove(artifactCollection.artifactCategories.size() - 1);
        }
        return artifactCollection;
    }

}
