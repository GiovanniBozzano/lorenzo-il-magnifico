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
* Advanced Game Rules
* ✔ Interfaces
	- ✔ GUI (main interface)
	- ✔ CLI (secondary interface)
* Network Types
	- ✔ RMI
	- ✔ Socket
* File Configurations
	- ✔ Development Cards
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
