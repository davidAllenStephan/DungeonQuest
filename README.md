<h1>Map Generator</h1>

![GitHub Release](https://img.shields.io/github/v/release/davidAllenStephan/map)
![GitHub License](https://img.shields.io/github/license/davidAllenStephan/map)
![GitHub Issues or Pull Requests](https://img.shields.io/github/issues/davidAllenStephan/map)
![GitHub last commit](https://img.shields.io/github/last-commit/davidAllenStephan/map)


This is built using the implementation guide made by Amit Patel at [http://www-cs-students.stanford.edu/~amitp/game-programming/polygon-map-generation/](http://www-cs-students.stanford.edu/~amitp/game-programming/polygon-map-generation/) 

Repository: [https://github.com/davidAllenStephan/map](https://github.com/davidAllenStephan/map)

Documentation: [https://davidallenstephan.github.io/map/](https://davidallenstephan.github.io/map/)

## Features
Procedurally generates maps
* Adjust elevation

## License
This project is licensed under the [MIT license](http://opensource.org/licenses/mit-license.php)

## Maps
### Voronoi Diagram
![Voronoi Diagram](/output/base_voronoi_diagram.png)
### Lloyd Relaxation
#### Applied 1 time
![Lloyd Relaxation applied 1 time](/output/lloyd_relaxation_1.png)
#### Applied 3 times
![Lloyd Relaxation applied 3 times](/output/lloyd_relaxation_3.png)
#### Applied 10 times
![Lloyd Relaxation applied 10 times](/output/lloyd_relaxation_10.png)
### Noisy Border
![Noisy Border](/output/noisy_border.png)
### Radial Height Map
![Radial Height Map](/output/radial_height_map.png)
### Coast Mask
![Coast Mask](/output/coast_mask.png)
### Coast Erosion
#### Not Applied
![Coast Erosion](/output/coast_erosion_0.png)
#### Applied
The coast is eroded by changing height index of polygons in a inward direction.
![Coast Erosion](/output/coast_erosion_1.png)
### All Combined
![All Combined](/output/all_combined.png)

## API
### Example
#### Request
```json
{
  "path": "/dungeon",
  "httpMethod": "POST",
  "headers": {
    "Content-Type": "application/json"
  },
  "body": "{\"height\":400,\"width\":400,\"numberOfPoints\":1000,\"numberOfRooms\":10,\"distanceBetweenRooms\":10,\"minimumRoomWidth\":20,\"maximumRoomWidth\":50,\"minimumRoomHeight\":20,\"maximumRoomHeight\":50,\"vertexSize\":5,\"edgeSize\":1,\"numberOfLloydIterations\":5,\"waterLevel\":0.4,\"coastLevel\":0.45,\"whiteCapLevel\":0.85,\"imageFileName\":\"output/map.png\",\"backgroundColor\":[255,255,255],\"polygonVertexColor\":[0,0,255],\"polygonSiteColor\":[255,0,0],\"polygonBorderColor\":[0,0,255],\"startPercent\":0.5,\"spreadChance\":0.8,\"maxChunks\":20,\"maxStepsPerChunk\":20}"
}
```
#### Response
```json
{
	"statusCode": 200,
	"headers": {
		"Content-Type": "image/png",
		"Access-Control-Allow-Origin": "*"
	},
	"body": "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",
	"isBase64Encoded": true
}
```
