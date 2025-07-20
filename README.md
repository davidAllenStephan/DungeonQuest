<h1>Map Generator</h1>

<h2>TODO</h2>
* <h3>Completeness – Usable, with clear README, install instructions, and working features.</h3>
* <h3>Tech Stack – Use frameworks and tools relevant to the jobs you’re applying for.</h3>
* <h3>Impact – Solves a problem or simulates a real-world use case.</h3>
* <h3>Visibility – Hosted/demoed on GitHub Pages, Vercel, Heroku, AWS, etc.</h3>
* <h3>Collaboration – If it was a group project, show how you used Git, issues, or CI/CD.</h3>

![GitHub Release](https://img.shields.io/github/v/release/davidAllenStephan/map)
![GitHub License](https://img.shields.io/github/license/davidAllenStephan/map)
![GitHub Issues or Pull Requests](https://img.shields.io/github/issues/davidAllenStephan/map)
![GitHub last commit](https://img.shields.io/github/last-commit/davidAllenStephan/map)


This is built using the implementation guide made by Amit Patel at [http://www-cs-students.stanford.edu/~amitp/game-programming/polygon-map-generation/](http://www-cs-students.stanford.edu/~amitp/game-programming/polygon-map-generation/) 

## License
This project is licensed under the [MIT license](http://opensource.org/licenses/mit-license.php)


## API
### Example
#### Request
```json
{
  "path": "/dungeonquest",
  "httpMethod": "POST",
  "headers": {
    "Content-Type": "application/json"
  },
  "body": {
    "map_scale": 4,
    "map_smoothing_count": 5,
    "water_level": 0.4,
    "coast_level": 0.45,
    "white_cap_level": 0.85,
    "erosion_start_percent": 0.5,
    "erosion_spread_percent": 0.8,
    "erosion_max_spread": 20,
    "erosion_max_steps": 20,
    "number_of_dungeons": 10,
    "number_of_rooms": 10,
    "minimum_room_width": 5,
    "maximum_room_width": 10,
    "minimum_room_height": 5,
    "maximum_room_height": 10,
    "common_item_chance": 0.5,
    "uncommon_item_chance": 0.3,
    "rare_item_chance": 0.15,
    "legendary_item_chance": 0.04,
    "mystical_item_chance": 0.01,
    "major_characters": ["Atticus Finch", "Indiana Jones", "Rick Blaine"],
    "monster_categories": ["Celtic mythology", "Egyptian mythology", "Greek mythology"],
    "artifact_categories": ["Armor", "Swords", "Shields"]
  }
}
```
#### Response
Note that all images are base64 encoded.

```json
    {
  "map": {
    "dungeonSites": {
      "sites": [
        {
          "dungeonSiteName": "...",
          "x": 0,
          "y": 0
        }
      ]
    },
    "mapImage": "..."
  },
  "dungeons": {
    "dungeons": [
      {
        "rooms": [
          {
            "roomImage": "...",
            "roomType": "..."
          }
        ]
      }
    ]
  },
  "quests": {
    "quests": [
      {
        "questLogs": [
          {
            "dungeonType": "...",
            "questLog": "..."
          }
        ]
      }
    ]
  }
}
```
