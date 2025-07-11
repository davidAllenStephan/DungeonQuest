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
    "mapScale":5,
    "numberOfRooms":10,
    "distanceBetweenRooms":10,
    "minimumRoomWidth":20,
    "maximumRoomWidth":50,
    "minimumRoomHeight":20,
    "maximumRoomHeight":50,
    "vertexSize":5,
    "edgeSize":1,
    "numberOfLloydIterations":5,
    "waterLevel":0.4,
    "coastLevel":0.45,
    "whiteCapLevel":0.85,
    "backgroundColor":[255,255,255],
    "polygonVertexColor":[0,0,255],
    "polygonSiteColor":[255,0,0],
    "polygonBorderColor":[0,0,255],
    "startPercent":0.5,
    "spreadChance":0.8,
    "maxChunks":20,
    "maxStepsPerChunk":20,
    "majorCharacters": ["Atticus Finch", "Indiana Jones", "Rick Blaine"],
    "monsterCategories": ["Celtic mythology", "Egyptian mythology", "Greek mythology"],
    "artifactCategories": ["Armor", "Swords", "Shields"]
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
            "dungeonImage": "..."
          }
        ]
      },
      "quests": {
        "quests": [
          {
            "questLogs": [
              {
                "roomType": "...",
                "questLog": "..."
              }
            ]
          }
        ]
      }
    }
```
