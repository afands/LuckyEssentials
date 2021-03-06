package dev.luckynetwork.id.lyrams.commands.features.pluginmanager

import com.google.common.base.Joiner
import dev.luckynetwork.id.lyrams.extensions.checkPermission
import dev.luckynetwork.id.lyrams.objects.Config
import dev.luckynetwork.id.lyrams.objects.PluginUtils
import dev.luckynetwork.id.lyrams.utils.SubCommand
import org.bukkit.command.CommandSender

class UsageCMD(name: String, vararg aliases: String) : SubCommand(name, *aliases) {

    override fun execute(sender: CommandSender, args: Array<out String>) {
        if (!sender.checkPermission("pluginmanager.usage"))
            return

        if (args.isEmpty()) {
            sender.sendMessage(Config.prefix + " §cPlease provide a plugin!")
            return
        }

        val plugin = PluginUtils.getPluginByName(args[0])

        if (plugin == null) {
            sender.sendMessage(Config.prefix + " §c§l${args[0]} §cnot found!")
            return
        }

        val usages = PluginUtils.getUsages(plugin)

        sender.sendMessage(Config.prefix + " §aCommands: §7$usages")



    }

}