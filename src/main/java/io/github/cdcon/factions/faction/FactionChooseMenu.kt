package io.github.cdcon.factions.faction

import io.github.cdcon.factions.Main
import net.kyori.adventure.text.Component
import net.projecttl.inventory.gui
import net.projecttl.inventory.util.InventoryType
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import java.lang.NullPointerException
import io.github.cdcon.factions.utility.utils

class FactionChooseMenu {
    fun inventory(player: Player) {
        val factionInterface = FactionInterface()
        val playerDataContainer = player.persistentDataContainer

        try {
            if (playerDataContainer.has(
                    NamespacedKey(Main.getPlugin(), "playerChosenFaction"),
                    PersistentDataType.INTEGER
                ) and
                (playerDataContainer.get(
                    NamespacedKey(Main.getPlugin(), "playerChosenFaction"),
                    PersistentDataType.INTEGER
                ) != 0)
            ) {
                player.sendMessage("Ты уже выбрал фракцию")
                player.closeInventory()
                return
            }
        } catch (e: NullPointerException){Main.getPlugin().logger.warning("Proizoshel pizdec v FactionsChooseMenu.kt:40")}


        player.gui(Component.text("Выбор фракции"), InventoryType.CHEST_27) {
            for (faction in factionInterface.struct){

                if (faction.id > 25){
                    Main.getPlugin().logger.warning("Too many factions!")
                    break
                }

                val factionButton = ItemStack(Material.SNOWBALL)
                val factionButtonMetadata: ItemMeta = factionButton.itemMeta
                val lore = utils.convertStringToComponent(faction.description)

                factionButtonMetadata.displayName(Component.text(faction.name))
                factionButtonMetadata.lore(lore)
                factionButtonMetadata.setCustomModelData(1488+faction.id)
                factionButton.itemMeta = factionButtonMetadata

                slot(faction.id - 1, factionButton) {
                    player.sendMessage("Выбран " + faction.name)
                    playerDataContainer.set(
                        NamespacedKey(Main.getPlugin(), "playerChosenFaction"),
                        PersistentDataType.INTEGER,
                        faction.id
                    )
                    player.closeInventory()
            }

            }
            val closeMenuButton = ItemStack(Material.OAK_DOOR)
            val closeMenuButtonMeta: ItemMeta = closeMenuButton.itemMeta

            closeMenuButtonMeta.displayName(Component.text("Я сделаю выбор позже"))
            closeMenuButton.itemMeta = closeMenuButtonMeta

            slot(26, closeMenuButton) {
                playerDataContainer.set(
                    NamespacedKey(Main.getPlugin(), "playerChosenFaction"),
                    PersistentDataType.INTEGER,
                    0
                )
                player.closeInventory()
            }
        }
    }
}
