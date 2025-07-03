package davidmarino.quest.questmodels;

public class HeroCharacter extends MajorCharacter {
    public HeroCharacter(String name, String sex, String actor, String film, String year, String notes) {
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
        return "\nname="+name+"\nsex="+sex+"\nactor="+actor+"\nfilm="+film+"\nyear="+year+"\nnotes="+notes+"\nclass="+HeroCharacter.class.getSimpleName();
    }
}
