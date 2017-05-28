# Design Description and Rationale
## Force and Mind Control
### Design Description
Each `SWActor` shall maintain its current level of force by means of an enumerated type. Four levels are specified:
1. None
1. WEAK
1. UNTRAINED
1. TRAINED

Only `NONE` force levels are weak enough to be vulnerable to mind control.

Mind control mechanics shall be implemented via a `Control` affordance on vulnerable actors.

### Design Justification
The four levels are enough to distinguish between non-force users and force users of low, medium and high levels. Adding an enumerated attribute to all `SWActor` types is intrusive, yet remains the simplest means of introducing force mechanics without a major overhaul of the class hierarchy. The `Control` affordance serves as a dual to the `Move` action.

#### Responsibilities: Force levels enumeration
* Track actor's current force level
* Allow getting/setting force
* Used to determine if vulnerable to Jedi mind tricks

#### Responsibilities: `Control`
* Affordance on vulnerable actors
* Allow actor to move target in a given direction

## Lightsabres
### Design Description
The `Attack` affordance code shall be changed to take into account the actor's force level before taking the lightsaber into account.
### Design Justification
Simplest possible enhancement. May not be maintainable long-term if more weapons are added. However, no new weapons are expected to be added and time constraints force our choice.
#### Responsibilities: `Attack`
* Added conditional branch for lightsaber where trained force users see its damage take effect

## Ben Kenobi (Training Luke)
### Design Description
A `Train` affordance shall be added to Ben Kenobi. The player can walk up to Ben Kenobi and invoke the action to have Ben train Luke. The affordance shall raise Luke's force to where he is able to wield the lightsaber.
### Design Justification
This is the simplest possible design in the design space: a no-brainer.
#### Responsibilities: `Train`
* Affordance on Ben Kenobi
* Raises the player's force to where he is able to wield a lightsaber

## Droids
### Design Description
Droids shall be added as a subclass under `SWActor`. All droids shall have the following affordances:
* `OwnDroid` - actors may take ownership of droids
* `Repair` - actors carrying droid parts may repair an immobile droid
* `Disassemble` - actors may disassemble an immobile droid to get its `DroidParts`
* `OilDroid` - R2D2 or any actor carrying an oil can may oil an active droid to repair it

`R2D2` and `C3P0` shall be subclasses of `Droid`. This allows them to add their own behavior when not owned by any actor and fall back to every droid's default `actDefault` method when they have owners.

### Design Justification
Droids either follow or perform some default set of actions. Given this, method overriding is the cleanest design.

#### Responsibilities: `Droid`
* Represents a droid instance
* Follows its owning actor or stays still if it doesn't have an owner
#### Responsibilities: `Disassemble`
* Affordance on `Droid` instances that are immobile
* Replaces the immobile `Droid` with a `DroidParts` instance at the same location
#### Responsibilities: `DroidParts`
* Represents a dismantled `Droid` instance
* Can be used via `Repair` affordance on a `Droid` that is immobile on the map
* Restores the immobile droid to full health
* Affords `Take` so that actors can pick it up

## Healing
### Design Description
A `Drink` affordance shall be added to the `Canteen` instances on the map. The affordance shall allow actors to replenish their health given that the `Canteen` is not depleted.

`Drink` affordance is generic for all actors and special-cased for `Droid` instances to allow oil-cans to never fully deplete.
### Design Justification
A single affordance for both `Droid` and regular `SWActor` instances allows us to avoid excessive design complexity by keeping the number of new classes small. Since both of them share similar functionality, it is possible to benefit from the commonality and only add a special check to allow `Droid` instances to heal without depleting their oil cans.
#### Responsibilities: `Drink`
* Affordance on `Canteeen` and `Droid` instances
* Replenishes actor/droid health
* Special condition to prevent oil-can from fully depleting

