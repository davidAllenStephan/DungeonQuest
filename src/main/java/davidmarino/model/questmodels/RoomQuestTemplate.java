package davidmarino.model.questmodels;

import davidmarino.model.dungeonmodels.ZoneType;

import java.util.EnumMap;
import java.util.Map;

public class RoomQuestTemplate {

    private static final Map<ZoneType, String> questTemplates = new EnumMap<>(ZoneType.class);

    static {
        questTemplates.put(ZoneType.ROOM,     "Advance, %s: the chamber ahead holds an unknown challenge.");
        questTemplates.put(ZoneType.CORRIDOR, "Move carefully, %s: the corridor twists and echoes with something unseen.");
        questTemplates.put(ZoneType.TREASURE, "The treasure room is close, %s—but it's never unguarded.");
        questTemplates.put(ZoneType.BOSS,     "Ready yourself, %s: the boss_ has been waiting just beyond this door.");
        questTemplates.put(ZoneType.PUZZLE,   "The door won’t budge, %s: solve the puzzle or remain trapped.");
        questTemplates.put(ZoneType.SAFE,     "Rest while you can, %s: this room is safe—for now.");
        questTemplates.put(ZoneType.SECRET,   "You found a hidden chamber, %s: what lies here wasn’t meant to be uncovered.");
        questTemplates.put(ZoneType.VOID,     "You've stepped into the void, %s: here, even the dungeon itself is uncertain.");
        questTemplates.put(ZoneType.ENTRANCE, "You stand at the dungeon’s entrance, %s: step forward to begin your descent.");
        questTemplates.put(ZoneType.EXIT,     "The exit is near, %s: make it through this last stretch and the dungeon releases you.");
    }

    public static String generate(ZoneType type, QuestContext context) {
        String template = questTemplates.getOrDefault(type, "You are in a strange place, %s.");
        return String.format(template, context.entity);
    }
}
