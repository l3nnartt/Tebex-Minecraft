package io.tebex.plugin.command.sub;

import com.velocitypowered.api.command.CommandSource;
import io.tebex.plugin.TebexPlugin;
import io.tebex.plugin.command.SubCommand;
import net.kyori.adventure.text.Component;

import java.util.concurrent.ExecutionException;

public class BanCommand extends SubCommand {
    public BanCommand(TebexPlugin platform) {
        super(platform, "ban", "tebex.ban");
    }

    @Override
    public void execute(CommandSource sender, String[] args) {
        TebexPlugin platform = getPlatform();

        String playerName = args[0];
        String reason = args[1];
        String ip = args[2];

        if (!platform.isSetup()) {
            sender.sendMessage(Component.text("§b[Tebex] §7This server is not connected to a webstore. Use /tebex secret to set your store key."));
            return;
        }

        try {
            boolean success = platform.getSDK().createBan(playerName, ip, reason).get();
            if (success) {
                sender.sendMessage(Component.text("§b[Tebex] §7Player banned successfully."));
            } else {
                sender.sendMessage(Component.text("§b[Tebex] §7Failed to ban player."));
            }
        } catch (InterruptedException | ExecutionException e) {
            sender.sendMessage(Component.text("§b[Tebex] §7Error while banning player: " + e.getMessage()));
        }
    }

    @Override
    public String getDescription() {
        return "Bans a player from using the webstore. Unbans can only be made via the web panel.";
    }

    @Override
    public String getUsage() {
        return "<playerName> <ip> <reason>";
    }
}
