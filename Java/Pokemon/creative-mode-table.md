# Requirement 4

**Title**:
_Expanded Time Functionality_

**Description**:
_Currently, the time periods in the game are limited to just day and night. This could be expanded via the addition of
extra 'special event' time periods into the game. Where before, there would just be a day and a night time period, now there is a chance that a unique time event may occur—such as a solar flare or a new moon. For these 'special event' time periods, the various in-game entities that perceive time will experience more 'stronger' versions of existing effects. During a solar flare or a new moon, certain Pokemon will become stronger or weaker, will heal or be hurt by a greater amount, or even be immobilized._

_In detail, during a solar flare:_
_Charmander, Charizard, and Charmeleon will be healed by 15 hp each turn, and will be stronger._
_Bulbasaur will be hurt by 10 hp each turn, will be weaker, and cannot move._
_Squirtle will be hurt by 15 hp each turn, will be weaker, and cannot move._

_During a new moon:_
_Charmander, Charizard, and Charmeleon will be hurt by 15 hp each turn, will be weaker, and cannot move._
_Bulbasaur will be healed by 10 hp each turn, and will be stronger._
_Squirtle will be healed by 15 hp each turn, and will be stronger._

**Explanation why it adheres to SOLID principles** (WHY):

-Our new functionality adheres to the Open-Closed principle, as the expansion of our time functionality is implemented
without modifying our existing classes and code related to time perception. New solarFlareEffect() and newMoonEffect() methods will be added to the classes implementing
TimePerception, which does not modify the existing dayEffect() and nightEffect() methods. Furthermore, the way in which we implement this new functionality by building off of our existing code for time functionality means that we also adhere to the Dependency Inversion Principle because the higher level modules do not depend on the lower level modules—our concrete classes depend upon abstractions, specifically the TimePerception interface. The higher level module in this case is our TimePerceptionManager which merely depends upon the TimePerception interface. The Single Responsibility Principle is also adhered to because once again, each class has only one responsibility. Each Pokemon class has the responsibility of executing the time effects, while the TimePerceptionManager only has the responsibility of signaling to each Pokemon class which timePeriodEffect method to call based off the time period it switches to.

| Requirements                                                                                                            | Features (HOW) / Your Approach / Answer                                                                                                                               |
| ----------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Must use at least two (2) classes from the engine package                                                               | _In implementing the additional functionality for the new time periods, we will use the following two classes from the engine package: Actor and Ground. We will add timePeriodEffect methods to the various Pokemon and Ground classes in the Pokemon game such as Charmander, Bulbasaur, Squirtle, Tree, Lava, and Puddle. The Pokemon classes extends Actor, while Lava and Puddle extends Ground and Tree is a SpawningGround which extends Ground._ |
| Must use/re-use at least one(1) existing feature (either from assignment 2 and/or fixed requirements from assignment 3) | _Reuses the existing functionality related to time that implements a day/night functionality._
| Must use existing or create new abstractions (e.g., abstract or interface, apart from the engine code)                                                                                                                                                                       | _Makes use of the existing abstraction of the TimePerception interface by adding new timePeriodEffect() methods in the interface, requiring classes that implement TimePerception to specify functionality for the new time periods._                  |                                                                                                                                                                       | 
| Must use existing or create new capabilities                                                                            | _Creates new capabilities such as the status that makes Pokemon stronger (BUFFED) during a solar flare or a new moon event, as well as the status that determines if a Pokemon can move a solar flare or a new moon event (CAN_MOVE)_                                                                                                                                                                      | 
---

