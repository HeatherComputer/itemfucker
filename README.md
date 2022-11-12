Itemfucker! A rather hacky pure-mixin mod that implements customisable overflow protection for item entities. Those crashloops are painful, are they not? It adds four gamerules, explained below. It is unlikely you'd need to edit any other than perhaps the second.

**All gamerules:**

`IF.killAllItems` -- Fairly self explanatory. It kills all items on their first tick, before any other processing is done. This is mainly used for overflow emergencies and shouldn't be required for a server that has already had the mod. *Default: `false`*

`IF.itemOverflowThreshold` -- The amount of entities, not including the item to be deleted, that need to be present within the defined range before the item is deleted. This counts all entities, not just items, and is now separate to vanilla's `maxEntityCramming` rule. *Default: `15`*

`IF.itemOverflowRange` -- The radius in blocks, including the item's own block, in which entities are searched for. Special-case behaviour when set to `1`, which matches the old version in that this would only count entities that are touching, rather than the same block. *Default: `2`*

`IF.itemMinimumLifetime` -- The minimum amount of time, in ticks, before an item entity can be deleted. Does not take effect if `IF.killAllItems` is `true`. *Default: `100`*

Master branch will be left empty, check the other branches for each minecraft version. MIT license applies to all versions.
