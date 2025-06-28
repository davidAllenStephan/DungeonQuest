package davidmarino.model.questmodels;

import davidmarino.model.dungeonmodels.ZoneType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class QuestLogCollection {

    public HashMap<ZoneType, QuestLogList> questLogCollectionMap = new HashMap<>();

    public QuestLogCollection() {
        questLogCollectionMap.put(ZoneType.ROOM, getRoomQuestLogs());
        questLogCollectionMap.put(ZoneType.CORRIDOR, getCorridorQuestLogs());
        questLogCollectionMap.put(ZoneType.TREASURE, getTreasureQuestLogs());
        questLogCollectionMap.put(ZoneType.BOSS, getBossQuestLogs());
        questLogCollectionMap.put(ZoneType.PUZZLE, getPuzzleQuestLogs());
        questLogCollectionMap.put(ZoneType.SAFE, getSafeQuestLogs());
        questLogCollectionMap.put(ZoneType.SECRET, getSecretQuestLogs());
        questLogCollectionMap.put(ZoneType.VOID, getVoidQuestLogs());
        questLogCollectionMap.put(ZoneType.ENTRANCE, getEntranceQuestLogs());
        questLogCollectionMap.put(ZoneType.EXIT, getExitQuestLogs());
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

    public static QuestLogList getRoomQuestLogs() {
        ArrayList<String> roomQuestLogs = new ArrayList<>();
        roomQuestLogs.add("entity_ has entered an empty room.");
        roomQuestLogs.add("entity_ finds remnants of a forgotten battle.");
        roomQuestLogs.add("entity_ senses a faint magical presence.");
        return new QuestLogList(roomQuestLogs, ZoneType.ROOM);
    }

    public static QuestLogList getCorridorQuestLogs() {
        ArrayList<String> corridorQuestLogs = new ArrayList<>();
        corridorQuestLogs.add("entity_ walks cautiously through a narrow corridor.");
        corridorQuestLogs.add("entity_ hears distant echoes from the corridor walls.");
        corridorQuestLogs.add("entity_ steps on loose stones—best tread carefully.");
        return new QuestLogList(corridorQuestLogs, ZoneType.CORRIDOR);
    }

    public static QuestLogList getTreasureQuestLogs() {
        ArrayList<String> treasureQuestLogs = new ArrayList<>();
        treasureQuestLogs.add("entity_ discovers a chest filled with gold and _item_.");
        treasureQuestLogs.add("entity_ uncovers a hidden compartment with _item_.");
        treasureQuestLogs.add("entity_ finds ancient treasure, untouched for centuries.");
        return new QuestLogList(treasureQuestLogs, ZoneType.TREASURE);
    }

    public static QuestLogList getBossQuestLogs() {
        ArrayList<String> bossQuestLogs = new ArrayList<>();
        bossQuestLogs.add("entity_ has entered the lair of _boss_name_.");
        bossQuestLogs.add("entity_ hears a roar — the final challenge awaits.");
        bossQuestLogs.add("The ground trembles as _boss_name_ appears before entity_.");
        return new QuestLogList(bossQuestLogs, ZoneType.BOSS);
    }

    public static QuestLogList getPuzzleQuestLogs() {
        ArrayList<String> puzzleQuestLogs = new ArrayList<>();
        puzzleQuestLogs.add("entity_ finds a mysterious mechanism covered in runes.");
        puzzleQuestLogs.add("A riddle is etched on the wall: '_riddle_text_'.");
        puzzleQuestLogs.add("entity_ begins solving an ancient puzzle device.");
        return new QuestLogList(puzzleQuestLogs, ZoneType.PUZZLE);
    }

    public static QuestLogList getSafeQuestLogs() {
        ArrayList<String> safeQuestLogs = new ArrayList<>();
        safeQuestLogs.add("entity_ finds a safe haven — time to rest.");
        safeQuestLogs.add("A calm aura fills this room. entity_ feels rejuvenated.");
        safeQuestLogs.add("entity_ lights a small fire and takes a moment to breathe.");
        return new QuestLogList(safeQuestLogs, ZoneType.SAFE);
    }

    public static QuestLogList getSecretQuestLogs() {
        ArrayList<String> secretQuestLogs = new ArrayList<>();
        secretQuestLogs.add("entity_ discovers a hidden passage behind a loose brick.");
        secretQuestLogs.add("entity_ triggers a secret mechanism — a wall shifts open.");
        secretQuestLogs.add("A whispered voice guides entity_ to a concealed chamber.");
        return new QuestLogList(secretQuestLogs, ZoneType.SECRET);
    }

    public static QuestLogList getVoidQuestLogs() {
        ArrayList<String> voidQuestLogs = new ArrayList<>();
        voidQuestLogs.add("entity_ steps into a realm where time stands still.");
        voidQuestLogs.add("Shadows flicker in the void as entity_ ventures deeper.");
        voidQuestLogs.add("The air grows heavy — entity_ is not alone in the void.");
        return new QuestLogList(voidQuestLogs, ZoneType.VOID);
    }

    public static QuestLogList getEntranceQuestLogs() {
        ArrayList<String> entranceQuestLogs = new ArrayList<>();
        entranceQuestLogs.add("entity_ stands before the dungeon’s entrance, heart pounding.");
        entranceQuestLogs.add("The gate creaks open — entity_ takes the first step inside.");
        entranceQuestLogs.add("entity_ enters the dungeon, determined to survive.");
        return new QuestLogList(entranceQuestLogs, ZoneType.ENTRANCE);
    }

    public static QuestLogList getExitQuestLogs() {
        ArrayList<String> exitQuestLogs = new ArrayList<>();
        exitQuestLogs.add("entity_ sees daylight ahead — the exit is near.");
        exitQuestLogs.add("entity_ escapes the dungeon, treasure in hand.");
        exitQuestLogs.add("The journey ends as entity_ steps out of the shadows.");
        return new QuestLogList(exitQuestLogs, ZoneType.EXIT);
    }
}