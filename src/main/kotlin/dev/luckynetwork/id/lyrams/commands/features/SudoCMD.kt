package dev.luckynetwork.id.lyrams.commands.features

import dev.luckynetwork.id.lyrams.extensions.checkPermission
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SudoCMD : CommandExecutor {

    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        commandName: String?,
        args: Array<out String>?
    ): Boolean {

        if (!sender!!.checkPermission("sudo"))
            return false

        var target: Player

        // casts target
        target =
                // if console executes this
            if (sender !is Player) {
                // console must specify a player
                if (args!!.isEmpty()) {
                    sender.sendMessage("§e§lLuckyEssentials §a/ §cInvalid usage!")
                    return false
                }

                if (Bukkit.getPlayer(args[0]) == null) {
                    sender.sendMessage("§e§lLuckyEssentials §a/ §cPlayer not found!")
                    return false
                }

                Bukkit.getPlayer(args[0])

                // if executed by player
            } else
                sender

        if (args!!.isEmpty()) {
            sender.sendMessage("§e§lLuckyEssentials §a/ §cPlease provide a player!")
            return false
        }

        if (sender is Player) {
            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage("§e§lLuckyEssentials §a/ §cPlayer not found!")
                return false
            }

            target = Bukkit.getPlayer(args[0]) as Player

        }

        if (target == sender) {
            sender.sendMessage("§e§lLuckyEssentials §a/ §cYou can't sudo yourself!")
            return false
        }

        if (args.size < 2) {
            sender.sendMessage("§e§lLuckyEssentials §a/ §cInvalid usage!")
            return false
        }

        val sb = StringBuilder()
        for (arg in args)
            sb.append(arg).append(" ")

        val argsAsString = sb.toString().split(target.name + " ")[1]

        if (!argsAsString.startsWith("c:"))
            executeCommand(target, argsAsString)
        else
            target.chat(argsAsString.split("c:")[1])

        return false

    }

}

fun executeCommand(sender: CommandSender, command: String) {
    var toExec = command
    if (command.startsWith("/"))
        toExec = command.replaceFirst("/", "")

    Bukkit.dispatchCommand(sender, toExec)
}