package davidmarino.quest.questmodels;

import java.util.UUID;

public class VillainCharacter extends MajorCharacter {

    public VillainCharacter(String name, String sex, String actor, String film, String year, String notes) {
        super.id = UUID.randomUUID().toString();
        super.name = name;
        super.sex = sex;
        super.actor = actor;
        super.film = film;
        super.year = year;
        super.notes = notes;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return "\nname="+name+"\nsex="+sex+"\nactor="+actor+"\nfilm="+film+"\nyear="+year+"\nnotes="+notes+"\nclass="+VillainCharacter.class.getSimpleName();
    }
}
