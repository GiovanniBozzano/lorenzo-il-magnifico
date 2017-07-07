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
	- 3 Excommunication cubes
	- **5** Marker Discs. Place 1 disc each on the 0 step of the Victory Points track, Military Points track, Faith Points track and 	**Prestige Points track** respectively. Place the last disc on the Turn Order track.
	- **3** Woods, **3** Stones, **4** Servants
	- The first player receives 5 Coins, the second player receives 6 Coins, the third player receives 7 Coins, the fourth player receives 8 Coins **and the fifth player receives 9 Coins**
	- 1 Personal Bonus Tile each (in this game mode, standard side cannot be used). The newly added tile is the following:
	<img align="center" src="../master/images/new_personal_bonus_tile.png?raw=true" alt="New Personal Bonus Tile" width="400">
	
* There are **6** action spaces in the Market. 
	- In addition to the 4 spaces available in the normal game, 2 more spaces are added. These are:
		- **Receive 2 Stones and 2 Woods**
		- **Receive 1 Victory Point and 1 Prestige Point**
* Council Privilege is a bonus of your choice between:
	- 1 Wood and 1 Stone / 2 Servants / 2 Coins / 2 Military Points / 1 Faith Poin / **1 Prestige Point**
* End of the game and final score
	- **Prestige Strength: If there is a tie between first players, they all gain 2x Prestige Points. If there is a tie between 		second players, they all gain 1x Prestige Points, while first player keeps gaining 2x Prestige Points.**
	
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
> [Cranio Creations](http://www.craniocreations.it/)
