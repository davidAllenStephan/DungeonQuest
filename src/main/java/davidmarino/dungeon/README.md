# DungeonQuest Dungeon

## Goal
Generate a collection of 2D dungeon maps. Each room in the dungeon has a type.
Each room type has a set of quests.

## Implementation
Create a semi random number generator.
Ignore points that fall within a distance to another point on a 2D plane.
Ignore points whose set box bound falls within another point or edge.
Generate a set of semi random points on a 2D plane.
Generate a Minimum Spanning Tree of all the points.
For each point in the MST find the next closest point and connect with a
corridor.