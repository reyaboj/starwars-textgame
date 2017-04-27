# Design Description and Rationale
## Force and Mind Control
### Design Description
A new superclass `SWForceActor` shall be added to the `SWActor` hierarchy. `SWActor` shall be made to extend from `SWForceActor`, and `SWForceActor` shall extend from `Actor` in the engine, essentially replacing `SWActor` as the current root of the `starwars` package's actor hierarchy.

A `Control` affordance shall be placed on all `SWForceActor` instances (and by extension, `SWActor` instances). This shall allow any `SWActor` with Force to control other actors with no Force.

### Design Justification
Adding Force and Mind-control forced a choice (pun intended) between two possible designs:
1. Add a subclass `SWForceActor` to `SWActor` and have all force users extend `SWForceActor`, namely: `Player` and `SWLegend`.
1. Have `SWForceActor` be the new root of the actor hierarchy: `SWActor` extends `SWForceActor` and `SWForceActor` extends `Actor`.

The second design was chosen because it allows all actors to inherit force-level behavior and actions/affordances to query the force-level for a given actor. The decision was made to abstract the interface out to `SWEntityInterface` and have `SWForceActor` implement it: essentially, the `SWEntityInterface` would be extended with force behavior methods so that affordances can use that information.

#### Responsibilities: `SWForceActor`
* Maintain state related to the level of force for a given actor
* Allow access to the current force level for the actor
* Store the force cutoff threshold for the actor to be vulnerable to mind-control
* Allow checking whether the actor is currently vulnerable to mind-control
* Allow raising the force level

#### Responsibilities: `Control`
* Affordance on all force users (i.e. subclasses of `SWForceActor`)
* Allows triggering a mind-controlled state on a vulnerable `SWForceActor`

## Lightsabres
### Design Description
Currently, there is no affordance that allows wielding the lightsaber. A `Wield` affordance shall be added to the lightsaber that would allow an actor to equip the lightsaber by mechanism of dynamically adding the `WEAPON` capability to the lightsaber given that the actor performing `Wield` has sufficient force.
### Design Justification
Any other design would be too complicated and would involve changing at least one other class beyond what the current design forces on us. In addition, the current design allows us to keep the `Attack` affordance untouched.

Assumption: lightsabers do not have `WEAPON` capability after construction.
#### Responsibilities: `Wield`
* Affordance on the lightsaber to allow an `SWForceActor` to "wield" it
* Effectively adds the `WEAPON` capability to the lightsaber if the actor performing `Wield` has sufficient force. This capability shall be retained even on dropping the lightsaber since force is assumed to never go down.

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
Droids are to be added as a subclass of `SWActor`: class `Droid`. Each `Droid` shall be associated with an owning `SWActor`. `Droid` instances shall have a `Drink` affordance that allows repair via drinking oil cans. `Drink` affordance is generic for all actors and special-cased for `Droid` instances to allow oil-cans to never fully deplete.

`Droid` classes use the `FollowOwner` behavior to follow their owning actor through the map. The `FollowOwner` behavior shall not include the badlands damage feature. The `Droid` instance's `act` method shall check the moved-to location and then call its own `takeDamage` method based on the result.

`Droid` classes shall have an overriden `takeDamage` method to trigger state to immobile once their health drops to zero. This state shall add the `Disassemble` affordance to `Droid`. The `Disassemble` affordance shall replace an immobile `Droid` on the map with a `DroidParts` instance.

Immboile `Droid` classes shall have the `Repair` affordance added to them by `takeDamage`. `SWActor` instances with the `REPAIR` capability shall be allowed to restore such droids to normal health provided they are carrying a `DroidParts` instance.
### Design Justification
`Drink` was kept as a common affordance for both `Droid` and `SWAction` instances to avoid duplicating the common "health replenish" logic. The added special condition for oil-can replenishing was considered a fair trade-off for the design.

The `FollowOwner` behavior allows reuse for other entities which might need such behavior.
#### Responsibilities: `Droid`
* Represents a droid instance
* Follows its owning actor using the `FollowOwner` behavior
#### Responsibilities: `Disassemble`
* Affordance on `Droid` instances that are immobile
* Replaces the immobile `Droid` with a `DroidParts` instance at the same location
#### Responsibilities: `DroidParts`
* Represents a dismantled `Droid` instance
* Can be used via `Repair` affordance on a `Droid` that is immobile on the map
* Restores the immobile droid to full health
#### Responsibilities: `FollowOwner`
* Encapsulates the Droid's owner-following behavior
* Serves as a helper for `Droid`'s `act` method

## Healing
### Design Description
A `Drink` affordance shall be added to the `Canteen` instances on the map. The affordance shall allow actors to replenish their health given that the `Canteen` is not depleted.

`Drink` affordance is generic for all actors and special-cased for `Droid` instances to allow oil-cans to never fully deplete.
### Design Justification
A single affordance for both `Droid` and regular `SWActor` instances allows us to avoid excessive design complexity by keeping the number of new classes small. Since both of them share similar functionality, it is possible to benefit from the commonality and only add a special check to allow `Droid` instances to heal without depleting their oil cans.
#### Responsibilities: `Drink`
* Affordance on `Canteeen` and `Droid` instances
* Replenishes actor/droid health
* Special condition to prevent oil-can from full depleting
