package com.github.r4ai.items

import com.github.r4ai.Main.Companion.plugin
import org.bukkit.*
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object TeleportBerry : CustomItem(
    name = "Teleport Berry",
    itemLore = listOf("Teleports you to the spawn point."),
    material = Material.SWEET_BERRIES,
    recipeShape = listOf(
        " B ",
        "BGB",
        " B "
    ),
    recipeIngredients = mapOf(
        'B' to Material.SWEET_BERRIES,
        'G' to Material.GOLD_NUGGET
    ),
    itemIsUnbreakable = false
) {
    // Save the location of the player to the item when they shift-left-click it
    @EventHandler
    fun onShiftLeftClick(e: PlayerInteractEvent) {
        val p = e.player
        val item = e.item ?: return
        if (this.isMatch(item)
            && e.action.name == "LEFT_CLICK_BLOCK"
            && p.isSneaking
        ) {
            e.isCancelled = true
            val clickedBlockLocation = e.clickedBlock?.location ?: return
            val oldTpLocation = getLocationFromItem(p.world, item).let {
                if (it == null) null
                else longArrayOf(
                    it.x.toLong(),
                    it.y.toLong(),
                    it.z.toLong()
                )
            }
            val tpLocation = saveLocationToItem(item, clickedBlockLocation) ?: return
            if (oldTpLocation != null && oldTpLocation contentEquals tpLocation) return
            p.sendMessage("Location has been set to ${tpLocation[0]}, ${tpLocation[1]}, ${tpLocation[2]}")
        }
    }

    // Teleport the player to the location saved in the item when they consume it
    @EventHandler
    fun onConsume(e: PlayerItemConsumeEvent) {
        val p = e.player
        val item = e.item ?: return
        if (this.isMatch(item)) {
            val location = getLocationFromItem(p.world, item) ?: run {
                e.isCancelled = true
                p.sendMessage("Set the location to teleport first!")
                p.sendMessage("Hold the item and shift-left-click to set the location.")
                return
            }
            p.teleport(location)
        }
    }

    // Show a particle effect when the player right-clicks or shift-left-clicks the item
    @EventHandler
    fun onRightClick(e: PlayerInteractEvent) {
        val p = e.player
        val item = e.item ?: return
        if (this.isMatch(item) &&
            ((e.action.name == "LEFT_CLICK_BLOCK" && p.isSneaking)
                    || (e.action.name.contains("RIGHT")))
        ) {
            p.world.spawnParticle(Particle.SPELL_WITCH, p.location, 20, 0.5, 1.0, 0.5)
        }
    }

    private fun saveLocationToItem(item: ItemStack, location: Location): LongArray? {
        val tpLocation = longArrayOf(
            location.x.toLong(),
            location.y.toLong(),
            location.z.toLong()
        )
        item.itemMeta = item.itemMeta?.apply {
            lore = listOf("Teleports you to ${tpLocation[0]}, ${tpLocation[1]}, ${tpLocation[2]}")
            persistentDataContainer.set(
                NamespacedKey(plugin, "tp_location"),
                PersistentDataType.LONG_ARRAY,
                tpLocation
            )
        } ?: return null
        return tpLocation
    }

    private fun getLocationFromItem(world: World, item: ItemStack): Location? {
        val tpLocation = item.itemMeta?.persistentDataContainer?.get(
            NamespacedKey(plugin, "tp_location"),
            PersistentDataType.LONG_ARRAY
        ) ?: return null
        return Location(
            world,
            tpLocation[0].toDouble(),
            tpLocation[1].toDouble(),
            tpLocation[2].toDouble()
        )
    }
}