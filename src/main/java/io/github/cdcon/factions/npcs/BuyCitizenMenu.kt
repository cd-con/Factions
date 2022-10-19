package io.github.cdcon.factions.npcs

import io.github.cdcon.factions.Main
import io.github.cdcon.factions.faction.FactionInterface
import io.github.cdcon.factions.utility.utils
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


class BuyCitizenMenu {
    fun inventory(player: Player) {
        player.gui(Component.text("Покупка жителя"), InventoryType.CHEST_18) {
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
                    ) == 0)
                ) {
                    player.sendMessage("Ты не можешь покупать NPC, пока не вступишь во фракцию. Такие дела.")
                    player.closeInventory()
                }
            } catch (e: NullPointerException){
                Main.getPlugin().logger.warning("Proizoshel pizdec v BuyCitizenMenu.kt:38")}


            player.gui(Component.text("Покупка NPC"), InventoryType.CHEST_27) {
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

                closeMenuButtonMeta.displayName(Component.text("Выход"))
                closeMenuButton.itemMeta = closeMenuButtonMeta

                slot(26, closeMenuButton) {
                    player.closeInventory()
                }


            }
        }
        }
    }
