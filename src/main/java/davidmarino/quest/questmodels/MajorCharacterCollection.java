package davidmarino.quest.questmodels;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MajorCharacterCollection {
    public ArrayList<MajorCharacter> majorCharacters;

    public MajorCharacterCollection() {
        majorCharacters = new ArrayList<>();
    }

    public MajorCharacterCollection(ArrayList<MajorCharacter> majorCharacters) {
        this.majorCharacters = majorCharacters;
    }

    @Override
    public String toString() {
        return majorCharacters.toString();
    }
}
