package org.canadamc.PhantomRemover

import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Listener {
    private val supportedVersion = "1.16 - 1.19"
    private val pluginVersion = "1.0"

    override fun onEnable() {
        println("noPhantomsPlugin for version $supportedVersion enabled!")

        // Register events so we can actually listen for creature spawn events
        server.pluginManager.registerEvents(this, this)
    }

    // Handle command execution
    override fun onCommand(sender: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        // Since this is a small plugin I will handle every command in this file
        when (cmd.name) {
            "nophantomsinfo" -> noPhantomsInfoCommand(sender, args)
            else -> return false
        }
        return true
    }

    /**
     * Handles the nophantomsinfo command
     * @param sender The player who used the command
     * @param args The arguments the user provided
     */
    private fun noPhantomsInfoCommand(sender: CommandSender, args: Array<out String>) {
        if (sender is Player) { // Check if sender is a player
            val p = sender as Player // Convert to player
            if (!p.world.getGameRuleValue(GameRule.SEND_COMMAND_FEEDBACK))
                return // Check gamerule and stop if send command feedback is false
        }

        sender.sendMessage("PhantonRemover by Jaden. (Kotlin Version)")
    }

    // Catch creature spawn event and block it if it is a phantom
    @EventHandler
    fun spawnEvent(event: CreatureSpawnEvent) {
        val entity: Entity = event.entity

        if (entity.type == EntityType.PHANTOM) {
            event.isCancelled = true
        }
    }
}
