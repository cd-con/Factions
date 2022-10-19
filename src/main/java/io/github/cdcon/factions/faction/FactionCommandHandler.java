package io.github.cdcon.factions.faction;

import io.github.cdcon.factions.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class FactionCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            getServer().getLogger().info("args length: " + args.length);
            if (args.length > 0 && Objects.equals(args[0], "join"))
            {
                FactionChooseMenu menu = new FactionChooseMenu();
                if (args.length > 1 && sender.hasPermission("factions.admin"))
                {
                    try {
                        sender.sendMessage("Открываем выбор фракции для " + args[1]);
                        menu.inventory(Objects.requireNonNull(getServer().getPlayer(args[1])));
                     }catch (NullPointerException e){
                        sender.sendMessage("Такого игрока не существует или он находится вне сети");
                    }
                }
                else{
                    menu.inventory(Objects.requireNonNull(getServer().getPlayer(sender.getName())));
                }

            /*
               Покинуть фракию добровольно
               TODO добавить наказание
               /faction leave
             */
            } else if (args.length > 0 && Objects.equals(args[0], "leave")) {

                PersistentDataContainer playerDataContainer = Objects.requireNonNull(((Player) sender).getPlayer()).getPersistentDataContainer();

                if (playerDataContainer.has(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER) && playerDataContainer.get(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER) != 0){
                    playerDataContainer.set(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER, 0);
                }

                sender.sendMessage("Ты покинул фракцию");

            /*
               Исколючить игрока из фракции
               /faction kick [имя]
             */
            } else if (args.length > 1 && Objects.equals(args[0], "kick") && sender.hasPermission("factions.faction.admin")) {
                PersistentDataContainer playerDataContainer = Objects.requireNonNull(getServer().getPlayer(args[1])).getPersistentDataContainer();
                PersistentDataContainer adminDataContainer = Objects.requireNonNull(((Player) sender).getPlayer()).getPersistentDataContainer();

                if (playerDataContainer.has(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER) && Objects.equals(playerDataContainer.get(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER), adminDataContainer.get(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER))){
                    sender.sendMessage("Игрок был изгнан из фракции");
                    Objects.requireNonNull(getServer().getPlayer(args[1])).sendMessage("Вы были изгнаны из фракции");
                    playerDataContainer.set(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER, 0);
                }

                sender.sendMessage("Вы не можете совершить этой действие");

            /*
            Сделать объявление во фракции
             */
            }else if(args.length > 1 && Objects.equals(args[0], "broadcast") && sender.hasPermission("factions.faction.admin")){
                Collection<? extends Player> onlinePlayers = getServer().getOnlinePlayers();
                PersistentDataContainer adminDataContainer = Objects.requireNonNull(((Player) sender).getPlayer()).getPersistentDataContainer();

                StringBuilder broadcastMessageString = new StringBuilder();
                broadcastMessageString.append("[Фракционное объявление] ");

                for (String word:args
                     ) {
                    if (!word.equals("broadcast")) {
                        broadcastMessageString.append(word);
                        broadcastMessageString.append(" ");
                    }

                }
                for (Player iterPlayer:onlinePlayers) {
                    PersistentDataContainer iterPlayerDataContainer = Objects.requireNonNull(iterPlayer).getPersistentDataContainer();
                    if (iterPlayerDataContainer.has(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER) && Objects.equals(iterPlayerDataContainer.get(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER), adminDataContainer.get(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER))) {
                        iterPlayer.sendMessage(broadcastMessageString.toString());
                    }
                }
            }
            else if(args.length > 0 && Objects.equals(args[0], "info")) {
                PersistentDataContainer playerDataContainer = Objects.requireNonNull(((Player) sender).getPlayer()).getPersistentDataContainer();
                if (playerDataContainer.has(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER) && Objects.equals(playerDataContainer.get(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER), 0)){
                    sender.sendMessage("Для этого действия нужно состоять в фракции");
                    return true;
                }
                // TODO
                FactionInterface factionInterface = new FactionInterface();

                sender.sendMessage("Информация о фракции.");
                sender.sendMessage(factionInterface.getStruct().get(playerDataContainer.get(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER)-1).name);
                sender.sendMessage(factionInterface.getStruct().get(playerDataContainer.get(new NamespacedKey(Main.getPlugin(), "playerChosenFaction"), PersistentDataType.INTEGER)-1).description.toString());

                // Похрюкаем?
                ((Player) sender).playSound(((Player) sender).getLocation(), Sound.ENTITY_PIG_AMBIENT, 1, 1);

            }
            else{
                sender.sendMessage("Неверный синтаксис команды или недостаточно полномочий для её исполнения");
                return true;
            }
        }else {
            sender.sendMessage("Команда не может быть выполнена в этой среде выполнения");
            return true;
        }
        return true;
    }
}
