package dev.luckynetwork.id.lyrams.commands.features.essentials

import dev.luckynetwork.id.lyrams.LuckyEssentials
import dev.luckynetwork.id.lyrams.extensions.checkPermission
import dev.luckynetwork.id.lyrams.extensions.checkPermissionSilent
import dev.luckynetwork.id.lyrams.extensions.colorizeTrueOrFalse
import dev.luckynetwork.id.lyrams.objects.Config
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ChatLockCMD : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandName: String,
        args: Array<out String>
    ): Boolean {

        if (!sender.checkPermission("chatlock"))
            return false

        var silent = false
        var anonymous = false

        if (args.isNotEmpty()) {
            when {
                args[0] == "-s" -> silent = true
                args[0] == "-a" -> anonymous = true
                else -> {
                    sendUsage(sender)
                    return false
                }
            }
        }

        LuckyEssentials.isChatLocked = !LuckyEssentials.isChatLocked
        val state = LuckyEssentials.isChatLocked

        if (!silent) {
            if (!anonymous)
                Bukkit.getOnlinePlayers().forEach {
                    it.sendMessage(" ")
                    if (!state)
                        it.sendMessage(Config.prefix + " §aChat has been unlocked by ${sender.name}!")
                    else
                        it.sendMessage(Config.prefix + " §cChat has been locked by ${sender.name}!")
                    it.sendMessage(" ")
                }
            else
                Bukkit.getOnlinePlayers().forEach {
                    it.sendMessage(" ")
                    if (!state)
                        it.sendMessage(Config.prefix + " §aChat has been unlocked by a staff member!")
                    else
                        it.sendMessage(Config.prefix + " §cChat has been locked by a staff member!")
                    it.sendMessage(" ")
                }
        } else {
            sender.sendMessage(Config.prefix + " §aChat lock: ${state.toString().colorizeTrueOrFalse()}")
        }

        Bukkit.getOnlinePlayers().forEach {
            if (it.checkPermissionSilent("chatlock"))
                if (state)
                    it.sendMessage(Config.prefix + " §c${sender.name} turned on chatlock")
                else
                    it.sendMessage(Config.prefix + " §c${sender.name} turned off chatlock")

        }

        return false

    }

}

private fun sendUsage(sender: CommandSender) {
    sender.sendMessage("§cUsage: /chatlock")
    sender.sendMessage("§cUsage: /chatlock -s")
    sender.sendMessage("§cUsage: /chatlock -a")
    sender.sendMessage("§cInfo: -s means silent. -a means anonymous")
}