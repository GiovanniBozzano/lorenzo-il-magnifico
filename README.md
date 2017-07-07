<h1 align="center">
	<img src="../master/images/cover.png?raw=true" alt="Lorenzo il Magnifico" width="400">
	<br />
	Lorenzo il Magnifico
	<br />
</h1>
<h4 align="center">Software Engineering</h4>
<p align="center">
	Politecnico di Milano - 2017
</p>
<br />

![screenshot](../master/images/demo.gif?raw=true)
<br />
<br />

## Implemented Features
* ✔ Advanced Game Rules
* Interfaces
	- ✔ GUI (main interface)
	- ✔ CLI (secondary interface)
* Network Types
	- ✔ RMI
	- ✔ Socket
* File Configurations
	- ✔ Cards
	- ✔ Game Board
	- ✔ Timers
* ✔ 5ᵗʰ Player
	- New Type of Points
	- New Personal Bonus Tile
	- New Market Slots
* ✔ Game Persistency
	- Players Registration
	- Disconnection/Reconnection Support
	- Score Saves
	
## 5ᵗʰ Player Rules

* Setup: Each player chooses a color and receives:
	- 1 Personal Board
	- 3 Family Members of their color and the uncolored Family Member with the sticker of their color
	- 3 Excommunication Cubes 
	- **5** Marker Discs. Place 1 disc each on the 0 step of the Victory Points track, Military Points track, Faith Points track and 	**Prestige Point track** respectively. Place the last disc on the Turn Order track.
	- **3** wood, **3** stone, **4** servants
	- The first player receives 5 coins, second player receives 6 coins, third player receives 7 coins, fourth player receives 8 		coins, **fifth player receives 9 coins**
	- 1 Personal Bonus Tile (in this type of game, standard side cannot be used). The new tile added is the following
	<h1 align="center">
	<img src="../master/images/new_tiles.png?raw=true" alt="Lorenzo il Magnifico" width="400">	
	
* There are **6** action spaces in the Market. 
	- In addition to the 4 spaces available in the normal game, 2 more spaces are added. These are:
		- **Receive 2 stone and 2 wood**
		- **Receive 1 victory point and 1 prestige point**

* Council Privilege is a bonus of your choice between:
	- 1 wood and 1 stone / 2 servants / 2 coins / 2 Military Points / 1 Faith Poin / **1 Prestige Point**

* Harvest and Production Area 
	- The second section gives a penalty of **-2** to the action value. To place a Family Member here, you must activate an action 		at value 1 or higher. 

* Towers for Development Cards 
	- If there are already Family Members in the same tower, pay **2** additional coins to the supply.

* End of the Game and Final Scoring 
	- **Prestige Strength: If there is a tie between first players, they all gain 2x Prestige Points. If there is a tie between 		second players, they all gain 1x Prestige Points, while first player keep gaining 2x Prestige Points.**
	
## Installation
To clone and run this game, you will need [Git](https://git-scm.com/), [Maven](https://maven.apache.org/) and [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/) installed on your computer. From your command line:
```bash
# Clone this Repository
$ git clone https://github.com/GiovanniBozzano/lorenzo-il-magnifico.git

# Go into the Repository
$ mvn package

# Run the Server
$ java -jar ./modules/server/target/LM13-server.jar

# Run the Client
$ java -jar ./modules/client/target/LM13-client.jar
```
Note: The Project is separated into modules: `Client`, `Server` and `Common`.
`Common` module is included in the others.

## Libraries
- [Gson](https://github.com/google/gson/) - JSON parsing
- [SQLite JDBC](https://bitbucket.org/xerial/sqlite-jdbc/) - SQLite database querying
- [JFoenix](https://github.com/jfoenixadmin/JFoenix/) - JavaFX theming

## Contributors
- Giovanni Bozzano - giovanni.bozzano@mail.polimi.it
- Manuel Candiani - manuel.candiani@mail.polimi.it
- Andrea Delfi - andrea.delfi@mail.polimi.it

---
> [Cranio Creations](http://www.craniocreations.it/)
