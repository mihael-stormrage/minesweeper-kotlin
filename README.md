# Minesweeper
Classic Minesweeper game CLI implementation

That's a project I've done while learning on [JetBrains Academy][hyperskill]

## Requirements
- Download [JDK][openjdk-14] and extract
- Add JDK `/bin` directory to your PATH

## Usage
- Download [release]
- Run where you placed `minesweeper.jar`:
```sh
java -jar minesweeper.jar
```
- Or you can run Kotlin class (see Build)
- Run in `/minesweeper` parent directory:
```sh
kotlin minesweeper.MainKt
```

## Build
- Install [Kotlin compiler][kotlinc], same as JDK (see requirements)
- Run the following from project directory


- To build artifact:
```sh
kotlinc src/minesweeper/Main.kt -include-runtime -d out/artifacts/minesweeper.jar
```
- To build Kotlin class:
```sh
kotlinc src/minesweeper/Main.kt -d out/production/classes
```

[openjdk-14]: http://jdk.java.net/14/
[kotlinc]: https://github.com/JetBrains/kotlin/releases/latest
[hyperskill]: https://hyperskill.org/projects/8
[release]: https://github.com/mihael-stormrage/minesweeper-kotlin/releases
