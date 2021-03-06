package dev.luckynetwork.id.lyrams.commands.features.essentials

import com.google.common.base.Joiner
import dev.luckynetwork.id.lyrams.extensions.checkPermission
import dev.luckynetwork.id.lyrams.extensions.getTargetPlayer
import dev.luckynetwork.id.lyrams.objects.Config
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class HealCMD : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        commandName: String,
        args: Array<out String>
    ): Boolean {
        if (!sender.checkPermission("heal"))
            return false

        val targets = args.getTargetPlayer(sender, 0)
        val targetNames = ArrayList<String>()

        if (targets.isEmpty())
            return false

        val others = !targets.contains(sender) || targets.size > 1

        if (!sender.checkPermission("heal", others))
            return false

        targets.forEach {
            it.health = 20.0
            it.foodLevel = 20
            it.sendMessage(Config.prefix + " §aYou have been healed!")
            targetNames.add(it.name)
        }


        if (others) {
            if (targets.size < 21)
                sender.sendMessage(Config.prefix + " §a§l" + Joiner.on(", ").join(targetNames) + " §ahave been healed!")
            else
                sender.sendMessage(Config.prefix + " §a§l" + targets.size + " players §ahave been healed!")

        }

        return false

    }

}