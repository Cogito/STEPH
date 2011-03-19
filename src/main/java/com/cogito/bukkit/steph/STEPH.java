package com.cogito.bukkit.steph;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.cogito.bukkit.bob.BankOfBanking;
import com.cogito.bukkit.conversations.ConversationAgent;
import com.cogito.bukkit.conversations.Conversations;

public class STEPH extends JavaPlugin {

    private Conversations conversations;
    private BankOfBanking bob;

    public void onDisable() {
        // TODO unload everything
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();

        // initialise fields
        try {
            conversations = (Conversations) pm.getPlugin("Conversations");
        } catch (Exception e) {
            System.out.println("'Conversations' is required to run "+this.getDescription().getName()+". Disabled.");
            pm.disablePlugin(this);
            return;
        }
        try {
            bob = (BankOfBanking) pm.getPlugin("BOB");
        } catch (Exception e) {
            System.out.println("'BOB' is required to run "+this.getDescription().getName()+". Disabled.");
            pm.disablePlugin(this);
            return;
        }

        // event registration

        // useful start up information
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName().toLowerCase();
        
        Player player = null;
        if (sender instanceof Player) {
            player = (Player)sender;
        }
        // do not parse any commands as a player unless the command sender is the player.

        if (commandName.equals("trade")) {
            return parseCommands(sender, player, args);
        }
        return false;
    }

    private boolean parseCommands(CommandSender sender, Player player, String[] args) {
        if (player == null){
            sender.sendMessage("Non player commands are not supported at this time.");
            return true;
        }
        SalesBroker salesBroker = new SalesBroker(this, player.getName());
        salesBroker.setAgent(conversations.startConversation(player.getName(), salesBroker));
        return salesBroker.parseReply(null, args);
    }

    public BankOfBanking bank() {
        return bob;
    }

}
