# LazyMon

by Mattia Metzler and Leoluca Bernardi, Group 42  
Disclaimer: this game is not lazily made even though the name might suggest this. We are both fans of
software such as lazygit and lazyvim, which is why we chose that name :)

## Contents
<!-- TOC -->
* [LazyMon](#lazymon)
  * [Contents](#contents)
  * [How to start](#how-to-start)
  * [Controls](#controls)
  * [Behaviour / Implementation details](#behaviour--implementation-details)
  * [How to play](#how-to-play)
<!-- TOC -->

## How to start

The game should be started "normally", by running the `icmon/src/main/java/ch/epfl/cs107/Play.java` file.  
Asserts are not necessary.

## Controls

| Key(s)    | Function       | Conditions                                             |
|-----------|----------------|--------------------------------------------------------|
| W / UP    | Walk up        | not in dialog                                          |
| A / LEFT  | Walk left      | not in dialog                                          |
| S / DOWN  | Walk down      | not in dialog                                          |
| D / RIGHT | Walk right     | not in dialog                                          |
| F         | Interact       | standing in front of interactable (Person, ball, etc.) |
| R         | Reset the game | -                                                      |
| SPACE     | Advance dialog | in dialog                                              |

All other controls are explained in the UI.

## Behaviour / Implementation details
The game behaves more or less as stated in the handout. Some implementation details can
be found in the `CONCEPTION.md` file.

## How to play
The game-loop is as follows:
1. The player spawns in his house and is prompted to either visit the Shop (blue roof) or the Lab (red rounded roof).
2. In the shop, the assistant will tell the player to go to the lab.
3. In the lab, Professor Oak gives the player their first Pokémon.
4. When going back to the Shop, the assistant will prompt the player to go fight Garry and to help her with finding her lost Pokémon.
5. After having fought Garry, which should always be possible (unless Garry's Pokémon escapes) as the player's Pokémon is slightly stronger, the player receives the Voltball Pokémon of Garry. This allows him to cross water bodies now.
6. Now the player can go look for the assistants Pokémon. If they find and defeat it or get defeated, that is the end of the game.