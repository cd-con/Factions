package io.github.cdcon.factions.npcs;

import io.github.cdcon.factions.npcs.BuyCitizenMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class CitizenCommandHandler implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0 && Objects.equals(args[0], "buy")) {
                BuyCitizenMenu menu = new BuyCitizenMenu();
                    menu.inventory(Objects.requireNonNull(getServer().getPlayer(sender.getName())));
                } else {sender.sendMessage(Component.text("Неверный синтаксис команды или недостаточно полномочий для её исполнения"));}
        } else {sender.sendMessage(Component.text("Команда не может быть выполнена в этой среде выполнения"));}
        return true;
    }
}
