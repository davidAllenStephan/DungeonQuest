package davidmarino.quest.questmodels;

public class MajorCharacter {
    public String name;
    public String sex;
    public String actor;
    public String film;
    public String year;
    public String notes;

    public MajorCharacter() {
        name = "";
        sex = "";
        actor = "";
        film = "";
        year = "";
        notes = "";
    }

    public MajorCharacter(String name, String sex, String actor, String film, String year, String notes) {
        this.name = name;
        this.sex = sex;
        this.actor = actor;
        this.film = film;
        this.year = year;
        this.notes = notes;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        return "\nname="+name+"\nsex="+sex+"\nactor="+actor+"\nfilm="+film+"\nyear="+year+"\nnotes="+notes+"\nclass="+MajorCharacter.class.getSimpleName();
    }
}
