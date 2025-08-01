package davidmarino.quest.questmodels;

import davidmarino.dungeon.dungeonmodels.enums.DungeonType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Component
public class QuestLogCollection {

    public HashMap<DungeonType, QuestLogList> questLogCollectionMap = new HashMap<>();

    public QuestLogCollection(MajorCharacterCollection majorCharacterCollection, ArtifactCollection artifactCollection, MonsterCollection monsterCollection) {
        Random rand = new Random();

        List<MajorCharacter> majors = majorCharacterCollection.getMajorCharacters();
        if (majors.isEmpty()) return;

        MajorCharacter majorCharacter = majors.get(rand.nextInt(majors.size()));

        List<ArtifactCategory> artifactCategories = artifactCollection.getArtifactCategories();
        List<MonsterCategory> monsterCategories = monsterCollection.getMonsterCategories();

        for (DungeonType dungeonType : DungeonType.values()) {
            Artifact artifact = getRandomArtifact(rand, artifactCategories);
            Monster monster = getRandomMonster(rand, monsterCategories);
            getQuestLogs(dungeonType, majorCharacter, artifact, monster);
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

    public void getQuestLogs(DungeonType dungeonType, MajorCharacter majorCharacter, Artifact artifact, Monster monster) {
        switch (dungeonType) {
            case TREASURE:
                questLogCollectionMap.put(DungeonType.TREASURE, getTreasureQuestLogs(majorCharacter, artifact));
                break;
            case BOSS:
                questLogCollectionMap.put(DungeonType.BOSS, getBossQuestLogs(majorCharacter, monster));
                break;
            case PUZZLE:
                questLogCollectionMap.put(DungeonType.PUZZLE, getPuzzleQuestLogs(majorCharacter));
                break;
            case SECRET:
                questLogCollectionMap.put(DungeonType.SECRET, getSecretQuestLogs(majorCharacter));
                break;
        }
    }

//    public static QuestLogList getUtilQuestLogs() {
//        ArrayList<String> utilQuestLogs = new ArrayList<>();
//        utilQuestLogs.add("To your left is a corridor to the _room_type room.");
//        utilQuestLogs.add("To your left is a dark corridor.");
//        utilQuestLogs.add("Directly ahead is a corridor to the _room_type room.");
//        utilQuestLogs.add("Directly ahead is a dark corridor.");
//        utilQuestLogs.add("To your right is a corridor to the _room_type room.");
//        utilQuestLogs.add("To your right is a dark corridor.");
//        return new QuestLogList(utilQuestLogs, RoomType.ROOM);
//    }

//    public static QuestLogList getRoomQuestLogs(MajorCharacter majorCharacter) {
//        ArrayList<String> roomQuestLogs = new ArrayList<>();
//        roomQuestLogs.add("entity_ has entered an empty room.");
//        roomQuestLogs.add("entity_ finds remnants of a forgotten battle.");
//        roomQuestLogs.add("entity_ senses a faint magical presence.");
//
//        roomQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));
//
//        return new QuestLogList(roomQuestLogs, RoomType.ROOM);
//    }

//    public static QuestLogList getCorridorQuestLogs(MajorCharacter majorCharacter) {
//        ArrayList<String> corridorQuestLogs = new ArrayList<>();
//        corridorQuestLogs.add("entity_ walks cautiously through a narrow corridor.");
//        corridorQuestLogs.add("entity_ hears distant echoes from the corridor walls.");
//        corridorQuestLogs.add("entity_ steps on loose stones—best tread carefully.");
//
//        corridorQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));
//
//        return new QuestLogList(corridorQuestLogs, RoomType.CORRIDOR);
//    }

    public static QuestLogList getTreasureQuestLogs(MajorCharacter majorCharacter, Artifact artifact) {
        ArrayList<String> treasureQuestLogs = new ArrayList<>();
        treasureQuestLogs.add("entity_ discovers a chest filled with gold and _item_.");
        treasureQuestLogs.add("entity_ uncovers a hidden compartment with _item_.");
        treasureQuestLogs.add("entity_ finds ancient treasure, untouched for centuries.");

        treasureQuestLogs.replaceAll(string -> string
                .replaceAll("entity_", majorCharacter.getName())
                .replaceAll("_item_", artifact.getName()));

        return new QuestLogList(treasureQuestLogs, DungeonType.TREASURE);
    }

    public static QuestLogList getBossQuestLogs(MajorCharacter majorCharacter, Monster monster) {
        ArrayList<String> bossQuestLogs = new ArrayList<>();
        bossQuestLogs.add("entity_ has entered the lair of _boss_name_.");
        bossQuestLogs.add("entity_ hears a roar — the final challenge awaits.");
        bossQuestLogs.add("The ground trembles as _boss_name_ appears before entity_.");

        bossQuestLogs.replaceAll(string -> string
                .replaceAll("entity_", majorCharacter.getName())
                .replaceAll("_boss_name_", monster.getName()));

        return new QuestLogList(bossQuestLogs, DungeonType.BOSS);
    }

    public static QuestLogList getPuzzleQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> puzzleQuestLogs = new ArrayList<>();
        puzzleQuestLogs.add("entity_ finds a mysterious mechanism covered in runes.");
        puzzleQuestLogs.add("entity_ begins solving an ancient puzzle device.");

        puzzleQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(puzzleQuestLogs, DungeonType.PUZZLE);
    }

//    public static QuestLogList getSafeQuestLogs(MajorCharacter majorCharacter) {
//        ArrayList<String> safeQuestLogs = new ArrayList<>();
//        safeQuestLogs.add("entity_ finds a safe haven — time to rest.");
//        safeQuestLogs.add("A calm aura fills this room. entity_ feels rejuvenated.");
//        safeQuestLogs.add("entity_ lights a small fire and takes a moment to breathe.");
//
//        safeQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));
//
//        return new QuestLogList(safeQuestLogs, RoomType.SAFE);
//    }

    public static QuestLogList getSecretQuestLogs(MajorCharacter majorCharacter) {
        ArrayList<String> secretQuestLogs = new ArrayList<>();
        secretQuestLogs.add("entity_ discovers a hidden passage behind a loose brick.");
        secretQuestLogs.add("entity_ triggers a secret mechanism — a wall shifts open.");
        secretQuestLogs.add("A whispered voice guides entity_ to a concealed chamber.");

        secretQuestLogs.replaceAll(s -> s.replaceAll("entity_", majorCharacter.getName()));

        return new QuestLogList(secretQuestLogs, DungeonType.SECRET);
    }

}