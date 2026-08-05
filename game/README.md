# Angry Egg

A simple 2D Java game built with Swing. The player moves around the screen, leaves a growing trail behind them, and the game loop runs at 60 FPS.

## Overview

This project is a small arcade-style prototype with:

- a game window created in Swing
- keyboard input handling for movement
- a player entity with animated directional sprites
- a growing snake-like body trail
- a game loop that updates and redraws the scene continuously

## Project Structure

- src/main/Main.java - entry point that creates the game window
- src/main/Gamepanel.java - main game panel, render loop, and screen dimensions
- src/main/KeyHandler.java - keyboard input handling
- src/entity/Entity.java - shared entity properties
- src/entity/Player.java - player movement, growth, and rendering
- src/entity/SnakeBody.java - trail/body segment logic
- src/resources/player/ - image assets for the player and body

## Controls

Use the following keys to move:

- W - move up
- A - move left
- S - move down
- D - move right

## How to Run

From the project root, compile and run the game with PowerShell:

```powershell
javac -d out (Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName })
java -cp out main.Main
```

You can also run it from VS Code by opening the Main class and using the Java run action.

## Notes

- The game window is fixed-size and currently runs in a simple top-down style.
- The player sprite and body images are loaded from the resources folder under src/resources/player.
- If image files are missing, the game will fall back to simple colored drawing.