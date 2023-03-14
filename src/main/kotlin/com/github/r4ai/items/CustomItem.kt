package com.github.r4ai.items

import com.github.r4ai.Main
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.World
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.persistence.PersistentDataType

/*
 * This class is a template for creating custom items.
 * To create a custom item, create a new class that extends this class.
 *
 * ## Constructor parameters
 *
 * @param name: The name of the item. Must be [a-z0-9/._-] and unique.
 * @param itemLore: The lore of the item.
 * @param material: The material of the item.
 * @param recipeShape: The shape of the recipe.
 * @param recipeIngredients: The ingredients of the recipe.
 * @param itemIsUnbreakable: Whether the item is unbreakable.
 *                           If null, the item will be unbreakable if it was unbreakable before.
 *
 * ## Utility methods
 *
 * @method getItem(): Returns the item.
 * @method getRecipe(): Returns the recipe.
 * @method isMatch(item: ItemStack): Returns whether the given `item` is equal to this `CustomItem`.
 *                                   This method is used to check if the item is the same as this `CustomItem`.
 */
open class CustomItem(
    private val name: String, // Name must be [a-z0-9/._-] and unique
    private val itemLore: List<String>?,
    private val material: Material,
    private val recipeShape: List<String>,
    private val recipeIngredients: Map<Char, Material>,
    private val itemIsUnbreakable: Boolean? = null,
    private val dataContainers: Map<String, Map<PersistentDataType<Any, Any>, Any>> = mapOf()
) : Listener {
    val itemId = name.lowercase().replace(' ', '_')
    val itemNamespacedKey = NamespacedKey(Main.plugin, itemId)

    fun getItem(): ItemStack {
        return ItemStack(material).apply {
            itemMeta = itemMeta?.apply {
                setDisplayName(name)
                lore = itemLore
                isUnbreakable = itemIsUnbreakable ?: isUnbreakable
                persistentDataContainer.set(itemNamespacedKey, PersistentDataType.BYTE, 1)

                dataContainers.forEach { (key, data) ->
                    val type = data.keys.first()
                    val value = data.values.first()
                    if (type.complexType::class.java.typeName == value::class.java.typeName) {
                        persistentDataContainer.set(NamespacedKey(Main.plugin, key), type, value)
                    } else {
                        Main.plugin.logger.warning("Item: $name, Data type mismatch for $key")
                    }
                }
            }
        }
    }

    fun getRecipe(): Recipe {
        return ShapedRecipe(itemNamespacedKey, getItem()).apply {
            shape(*recipeShape.toTypedArray())
            recipeIngredients.forEach { (key, material) ->
                setIngredient(key, material)
            }
        }
    }

    /**
     * Check if the given item is the same as this item.
     */
    fun isMatch(item: ItemStack): Boolean {
        return item.type == material && item.itemMeta?.persistentDataContainer?.has(
            itemNamespacedKey,
            PersistentDataType.BYTE
        ) == true
    }

    /**
     * Convert location to long array.
     * - `array[0] = x`
     * - `array[1] = y`
     * - `array[2] = z`
     */
    fun locationToLongArray(location: Location): LongArray {
        return longArrayOf(
            location.x.toLong(),
            location.y.toLong(),
            location.z.toLong()
        )
    }

    /**
     * Convert long array to location.
     * - `x = array[0]`
     * - `y = array[1]`
     * - `z = array[2]`
     */
    fun longArrayToLocation(world: World, longArray: LongArray): Location {
        return Location(
            world,
            longArray[0].toDouble(),
            longArray[1].toDouble(),
            longArray[2].toDouble()
        )
    }

    /**
     * Get location from item persistent data.
     * If the item does not have a location, return null.
     */
    fun getLocationFromItem(world: World, item: ItemStack): Location? {
        val tpLocation = item.itemMeta?.persistentDataContainer?.get(
            NamespacedKey(Main.plugin, "tp_location"),
            PersistentDataType.LONG_ARRAY
        ) ?: return null
        return longArrayToLocation(world, tpLocation)
    }

    /**
     * Save location to item persistent data.
     * - If failed, return null.
     * - If successful, return the location converted to long array.
     */
    fun saveLocationToItem(item: ItemStack, location: Location): LongArray? {
        val tpLocation = locationToLongArray(location)
        item.itemMeta = item.itemMeta?.apply {
            lore = listOf("Teleports you to ${tpLocation[0]}, ${tpLocation[1]}, ${tpLocation[2]}")
            persistentDataContainer.set(
                NamespacedKey(Main.plugin, "tp_location"),
                PersistentDataType.LONG_ARRAY,
                tpLocation
            )
        } ?: return null
        return tpLocation
    }
}
