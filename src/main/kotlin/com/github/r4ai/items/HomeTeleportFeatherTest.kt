import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import be.seeseemelk.mockbukkit.entity.PlayerMock
import com.github.r4ai.Main
import com.github.r4ai.items.HomeTeleportFeather
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HomeTeleportFeatherTest {

    private lateinit var server: ServerMock
    private lateinit var plugin: Main

    @BeforeEach
    fun setUp() {
        // Start the mock server
        server = MockBukkit.mock()
        // Load the plugin
        plugin = MockBukkit.load(Main::class.java)
    }

    @Test
    fun testOnRightClick() {
        server.pluginManager.registerEvents(HomeTeleportFeather, plugin)

        // Create a mock player
        val player = PlayerMock(server, "Player")
        player.teleport(Location(player.world, 100.0, 5.0, 100.0))
        player.clearTeleported()

        val expectedLocation = player.bedSpawnLocation ?: player.world.spawnLocation

        // Give the player the item
        player.inventory.setItemInMainHand(HomeTeleportFeather.getItem())
        assertEquals(HomeTeleportFeather.getItem(), player.inventory.itemInMainHand)

        val event = PlayerInteractEvent(
            player, Action.RIGHT_CLICK_AIR, HomeTeleportFeather.getItem(), null, BlockFace.NORTH
        )
        HomeTeleportFeather.onRightClick(event)
        server.pluginManager.callEvent(event)
        server.scheduler.performTicks(50L)

        println(player.hasTeleported())
        println(player.location)
        println(expectedLocation)
    }

    @AfterEach
    fun tearDown() {
        // Stop the mock server
        MockBukkit.unmock()
    }
}
