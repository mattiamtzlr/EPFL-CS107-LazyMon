# Conception

## Contents
<!-- TOC -->
* [Conception](#conception)
  * [Contents](#contents)
  * [Added classes / interfaces](#added-classes--interfaces)
  * [Changes to the architecture](#changes-to-the-architecture)
    * [Changes to the game engine](#changes-to-the-game-engine)
    * [Implementation differences](#implementation-differences)
    * [Minor alterations from the énoncé](#minor-alterations-from-the-énoncé)
  * [Extensions](#extensions)
<!-- TOC -->

## Added classes / interfaces
- `/.../icmon/graphics/PokemonSelectionMenuGraphics.java`, as it is a type of graphics, it seemed fitting to put it with the other graphics.
- TODO

## Changes to the architecture
### Changes to the game engine
- Changed the MAX_LINE_LENGTH constant in Dialog.java to 50, because it looks better to have longer lines.
### Implementation differences
- Added some depth to the AreaBehavior.java class
  - Only ICMonPlayer instances that do have the Pokemon "voltball" can actually enter the water and swim (cross cells with AllowedWalkingtype.SURF). For this we added the hasSurf() method to ICMonPlayer and checked it in ICMonAreaBehavior.
### Minor alterations from the énoncé
- We used english names throughout and changed filenames accordingly
- The keys "WASD" can also be used to move
- Using "F" instead of "L" to interact, as it feels more in tune with what is often the hotkey for interactions
- Moved actors to new locations based on our vision for a short gameplay loop
- Changed the door through which the lab and the arena are accessed in order to match what is written on the buildings
- Changed the background of the pokemon selection pause menu
- Changed some of the resource files inside the dialogs directory to improve immersion :)

## Extensions
- added various new Pokemon sprites and corresponding classes:
  - Voltball
  - Enton
  - Gengar
  - Enton
  - Pikachu
  - Tentacool
- Created classes for events inside the package "gamelogic/events" to fit our needs:
  - EndBossEvent
- Created classes for actions inside the package "gamelogic/actions" to fit our needs:
  - GivePokemonAction
  - OpenDialogAction
- Created classes for messages inside the package "gamelogic/messages" to fit our needs:
  - IndependentDialogMessage
