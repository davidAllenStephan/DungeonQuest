package davidmarino.model.questmodels;

import java.util.ArrayList;

public class QuestLog {

    public static ArrayList<String> getUtilQuestLogs() {
        ArrayList<String> utilQuestLogs = new ArrayList<>();

        utilQuestLogs.add("To your left is a corridor to the _room_type room.");
        utilQuestLogs.add("To your left is a dark corridor.");
        utilQuestLogs.add("Directly ahead is a corridor to the _room_type room.");
        utilQuestLogs.add("Directly ahead is a dark corridor.");
        utilQuestLogs.add("To your right is a corridor to the _room_type room.");
        utilQuestLogs.add("To your right is a dark corridor.");

        return utilQuestLogs;
    }

    public static ArrayList<String> getRoomQuestLogs() {
        ArrayList<String> roomQuestLogs = new ArrayList<>();

        roomQuestLogs.add("entity_ has entered an empty room.");
        roomQuestLogs.add("entity_ finds remnants of a forgotten battle.");
        roomQuestLogs.add("entity_ senses a faint magical presence.");

        return roomQuestLogs;
    }

    public static ArrayList<String> getCorridorQuestLogs() {
        ArrayList<String> corridorQuestLogs = new ArrayList<>();

        corridorQuestLogs.add("entity_ walks cautiously through a narrow corridor.");
        corridorQuestLogs.add("entity_ hears distant echoes from the corridor walls.");
        corridorQuestLogs.add("entity_ steps on loose stones—best tread carefully.");

        return corridorQuestLogs;
    }

    public static ArrayList<String> getTreasureQuestLogs() {
        ArrayList<String> treasureQuestLogs = new ArrayList<>();

        treasureQuestLogs.add("entity_ discovers a chest filled with gold and _item_.");
        treasureQuestLogs.add("entity_ uncovers a hidden compartment with _item_.");
        treasureQuestLogs.add("entity_ finds ancient treasure, untouched for centuries.");

        return treasureQuestLogs;
    }

    public static ArrayList<String> getBossQuestLogs() {
        ArrayList<String> bossQuestLogs = new ArrayList<>();

        bossQuestLogs.add("entity_ has entered the lair of _boss_name_.");
        bossQuestLogs.add("entity_ hears a roar — the final challenge awaits.");
        bossQuestLogs.add("The ground trembles as _boss_name_ appears before entity_.");

        return bossQuestLogs;
    }

    public static ArrayList<String> getPuzzleQuestLogs() {
        ArrayList<String> puzzleQuestLogs = new ArrayList<>();

        puzzleQuestLogs.add("entity_ finds a mysterious mechanism covered in runes.");
        puzzleQuestLogs.add("A riddle is etched on the wall: '_riddle_text_'.");
        puzzleQuestLogs.add("entity_ begins solving an ancient puzzle device.");

        return puzzleQuestLogs;
    }

    public static ArrayList<String> getSafeQuestLogs() {
        ArrayList<String> safeQuestLogs = new ArrayList<>();

        safeQuestLogs.add("entity_ finds a safe haven — time to rest.");
        safeQuestLogs.add("A calm aura fills this room. entity_ feels rejuvenated.");
        safeQuestLogs.add("entity_ lights a small fire and takes a moment to breathe.");

        return safeQuestLogs;
    }

    public static ArrayList<String> getSecretQuestLogs() {
        ArrayList<String> secretQuestLogs = new ArrayList<>();

        secretQuestLogs.add("entity_ discovers a hidden passage behind a loose brick.");
        secretQuestLogs.add("entity_ triggers a secret mechanism — a wall shifts open.");
        secretQuestLogs.add("A whispered voice guides entity_ to a concealed chamber.");

        return secretQuestLogs;
    }

    public static ArrayList<String> getVoidQuestLogs() {
        ArrayList<String> voidQuestLogs = new ArrayList<>();

        voidQuestLogs.add("entity_ steps into a realm where time stands still.");
        voidQuestLogs.add("Shadows flicker in the void as entity_ ventures deeper.");
        voidQuestLogs.add("The air grows heavy — entity_ is not alone in the void.");

        return voidQuestLogs;
    }

    public static ArrayList<String> getEntranceQuestLogs() {
        ArrayList<String> entranceQuestLogs = new ArrayList<>();

        entranceQuestLogs.add("entity_ stands before the dungeon’s entrance, heart pounding.");
        entranceQuestLogs.add("The gate creaks open — entity_ takes the first step inside.");
        entranceQuestLogs.add("entity_ enters the dungeon, determined to survive.");

        return entranceQuestLogs;
    }

    public static ArrayList<String> getExitQuestLogs() {
        ArrayList<String> exitQuestLogs = new ArrayList<>();

        exitQuestLogs.add("entity_ sees daylight ahead — the exit is near.");
        exitQuestLogs.add("entity_ escapes the dungeon, treasure in hand.");
        exitQuestLogs.add("The journey ends as entity_ steps out of the shadows.");

        return exitQuestLogs;
    }
}