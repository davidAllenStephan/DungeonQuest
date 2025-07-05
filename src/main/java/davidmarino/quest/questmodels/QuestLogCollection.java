package davidmarino.quest.questmodels;

import davidmarino.dungeon.dungeonmodels.ZoneType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component
public class QuestLogCollection {

    public HashMap<ZoneType, QuestLogList> questLogCollectionMap = new HashMap<>();

    public QuestLogCollection(MajorCharacterCollection majorCharacterCollection, ArtifactCollection artifactCollection, MonsterCollection monsterCollection) {
        Random rand = new Random();

        List<MajorCharacter> majors = majorCharacterCollection.getMajorCharacters();
        if (majors.isEmpty()) return;

        MajorCharacter majorCharacter = majors.get(rand.nextInt(majors.size()));

        List<ArtifactCategory> artifactCategories = artifactCollection.getArtifactCategories();
        List<MonsterCategory> monsterCategories = monsterCollection.getMonsterCategories();

        for (ZoneType zoneType : ZoneType.values()) {
            Artifact artifact = getRandomArtifact(rand, artifactCategories);
            Monster monster = getRandomMonster(rand, monsterCategories);
            getQuestLogs(zoneType, majorCharacter, artifact, monster);
        }
    }

    private Artifact getRandomArtifact(Random rand, List<ArtifactCategory> artifactCategories) {
        if (artifactCategories.isEmpty()) return null;
        ArtifactCategory category = artifactCategories.get(rand.nextInt(artifactCategories.size()));
        List<ArtifactSubCategory> subCategories = category.getArtifactSubCategories();
        if (subCategories.isEmpty()) return null;
        ArtifactSubCategory subCategory = subCategories.get(rand.nextInt(subCategories.size()));
        List<Artifact> artifacts = subCategory.getArtifacts();
        if (artifacts.isEmpty()) return null;
        return artifacts.get(rand.nextInt(artifacts.size()));
    }

    private Monster getRandomMonster(Random rand, List<MonsterCategory> monsterCategories) {
        if (monsterCategories.isEmpty()) return null;
        MonsterCategory category = monsterCategories.get(rand.nextInt(monsterCategories.size()));
        List<Monster> monsters = category.getMonsters();
        if (monsters.isEmpty()) return null;
        return monsters.get(rand.nextInt(monsters.size()));
    }

    public void getQuestLogs(ZoneType zoneType, MajorCharacter majorCharacter, Artifact artifact, Monster monster) {
        switch (zoneType) {
            case ROOM:
                questLogCollectionMap.put(ZoneType.ROOM, getRoomQuestLogs(majorCharacter));
                break;
            case CORRIDOR:
                questLogCollectionMap.put(ZoneType.CORRIDOR, getCorridorQuestLogs(majorCharacter));
                break;
            case TREASURE:
                questLogCollectionMap.put(ZoneType.TREASURE, getTreasureQuestLogs(majorCharacter, artifact));
                break;
            case BOSS:
                questLogCollectionMap.put(ZoneType.BOSS, getBossQuestLogs(majorCharacter, monster));
                break;
            case PUZZLE:
                questLogCollectionMap.put(ZoneType.PUZZLE, getPuzzleQuestLogs(majorCharacter));
                break;
            case SAFE:
                questLogCollectionMap.put(ZoneType.SAFE, getSafeQuestLogs(majorCharacter));
                break;
            case SECRET:
                questLogCollectionMap.put(ZoneType.SECRET, getSecretQuestLogs(majorCharacter));
                break;
            case VOID:
                questLogCollectionMap.put(ZoneType.VOID, getVoidQuestLogs(majorCharacter));
                break;
            case ENTRANCE:
                questLogCollectionMap.put(ZoneType.ENTRANCE, getEntranceQuestLogs(majorCharacter));
                break;
            case EXIT:
                questLogCollectionMap.put(ZoneType.EXIT, getExitQuestLogs(majorCharacter));
                break;
        }
    }

    public static QuestLogList getUtilQuestLogs() {
        ArrayList<String> utilQuestLogs = new ArrayList<>();
        utilQuestLogs.add("To your left is a corridor to the _room_type room.");
        utilQuestLogs.add("To your left is a dark corridor.");
        utilQuestLogs.add("Directly ahead is a corridor to the _room_type room.");
        utilQuestLogs.add("Directly ahead is a dark corridor.");
        utilQuestLogs.add("To your right is a corridor to the _room_type room.");
        utilQuestLogs.add("To your right is a dark corridor.");
        return new QuestLogList(utilQuestLogs, ZoneType.ROOM);
    }

    public static QuestLogList getRoomQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> roomQuestLogs = new ArrayList<>();
        roomQuestLogs.add("entity_ has entered an empty room.");
        roomQuestLogs.add("entity_ finds remnants of a forgotten battle.");
        roomQuestLogs.add("entity_ senses a faint magical presence.");

        roomQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(roomQuestLogs, ZoneType.ROOM);
    }

    public static QuestLogList getCorridorQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> corridorQuestLogs = new ArrayList<>();
        corridorQuestLogs.add("entity_ walks cautiously through a narrow corridor.");
        corridorQuestLogs.add("entity_ hears distant echoes from the corridor walls.");
        corridorQuestLogs.add("entity_ steps on loose stones—best tread carefully.");

        corridorQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(corridorQuestLogs, ZoneType.CORRIDOR);
    }

    public static QuestLogList getTreasureQuestLogs(MajorCharacter majorCharacter, Artifact artifact) {
        ArrayList<String> treasureQuestLogs = new ArrayList<>();
        treasureQuestLogs.add("entity_ discovers a chest filled with gold and _item_.");
        treasureQuestLogs.add("entity_ uncovers a hidden compartment with _item_.");
        treasureQuestLogs.add("entity_ finds ancient treasure, untouched for centuries.");

        treasureQuestLogs.replaceAll(string -> string
                .replaceAll("entity_", majorCharacter.getName())
                .replaceAll("_item_", artifact.getName()));

        return new QuestLogList(treasureQuestLogs, ZoneType.TREASURE);
    }

    public static QuestLogList getBossQuestLogs(MajorCharacter majorCharacter, Monster monster) {
        ArrayList<String> bossQuestLogs = new ArrayList<>();
        bossQuestLogs.add("entity_ has entered the lair of _boss_name_.");
        bossQuestLogs.add("entity_ hears a roar — the final challenge awaits.");
        bossQuestLogs.add("The ground trembles as _boss_name_ appears before entity_.");

        bossQuestLogs.replaceAll(string -> string
                .replaceAll("entity_", majorCharacter.getName())
                .replaceAll("_boss_name_", monster.getName()));

        return new QuestLogList(bossQuestLogs, ZoneType.BOSS);
    }

    public static QuestLogList getPuzzleQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> puzzleQuestLogs = new ArrayList<>();
        puzzleQuestLogs.add("entity_ finds a mysterious mechanism covered in runes.");
        puzzleQuestLogs.add("entity_ begins solving an ancient puzzle device.");

        puzzleQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(puzzleQuestLogs, ZoneType.PUZZLE);
    }

    public static QuestLogList getSafeQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> safeQuestLogs = new ArrayList<>();
        safeQuestLogs.add("entity_ finds a safe haven — time to rest.");
        safeQuestLogs.add("A calm aura fills this room. entity_ feels rejuvenated.");
        safeQuestLogs.add("entity_ lights a small fire and takes a moment to breathe.");

        safeQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(safeQuestLogs, ZoneType.SAFE);
    }

    public static QuestLogList getSecretQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> secretQuestLogs = new ArrayList<>();
        secretQuestLogs.add("entity_ discovers a hidden passage behind a loose brick.");
        secretQuestLogs.add("entity_ triggers a secret mechanism — a wall shifts open.");
        secretQuestLogs.add("A whispered voice guides entity_ to a concealed chamber.");

        secretQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(secretQuestLogs, ZoneType.SECRET);
    }

    public static QuestLogList getVoidQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> voidQuestLogs = new ArrayList<>();
        voidQuestLogs.add("entity_ steps into a realm where time stands still.");
        voidQuestLogs.add("Shadows flicker in the void as entity_ ventures deeper.");
        voidQuestLogs.add("The air grows heavy — entity_ is not alone in the void.");

        voidQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(voidQuestLogs, ZoneType.VOID);
    }

    public static QuestLogList getEntranceQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> entranceQuestLogs = new ArrayList<>();
        entranceQuestLogs.add("entity_ stands before the dungeon’s entrance, heart pounding.");
        entranceQuestLogs.add("The gate creaks open — entity_ takes the first step inside.");
        entranceQuestLogs.add("entity_ enters the dungeon, determined to survive.");

        entranceQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(entranceQuestLogs, ZoneType.ENTRANCE);
    }

    public static QuestLogList getExitQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> exitQuestLogs = new ArrayList<>();
        exitQuestLogs.add("entity_ sees daylight ahead — the exit is near.");
        exitQuestLogs.add("entity_ escapes the dungeon, treasure in hand.");
        exitQuestLogs.add("The journey ends as entity_ steps out of the shadows.");

        exitQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(exitQuestLogs, ZoneType.EXIT);
    }

}