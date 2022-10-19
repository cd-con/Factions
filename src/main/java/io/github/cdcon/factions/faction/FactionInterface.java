package io.github.cdcon.factions.faction;

import io.github.cdcon.factions.Main;
import io.github.cdcon.factions.utility.ConfigInterface;
import io.github.cdcon.factions.utility.structs.FactionStruct;
import io.github.cdcon.factions.utility.utils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FactionInterface {

    private List<FactionStruct> struct = new ArrayList<>();
    private ConfigInterface cfgInterface = new ConfigInterface();

    public void initFactions(){
        cfgInterface.defaultConfig();
        getFactionsFromConfig();
    }

    public void disableFactions(){
        cfgInterface.saveConfig();
    }

    private void getFactionsFromConfig(){
        ConfigurationSection factions_section = Main.pluginInstance.getConfig().getConfigurationSection("factions");
        assert factions_section != null;

        List<Object>factionsListConfig = new ArrayList<>(factions_section.getKeys(false));

        for (Object factionConfigSection: factionsListConfig){
            ConfigurationSection faction_section = Main.pluginInstance.getConfig().getConfigurationSection("factions."+factionConfigSection);

            assert faction_section != null;
            if (Objects.equals(faction_section.get("enabled"), false)){
                continue;
            }
            FactionStruct temp_struct = new FactionStruct();

            temp_struct.id = factionsListConfig.indexOf(factionConfigSection) + 1;
            temp_struct.players_can_join = Objects.equals(faction_section.get("players_can_join"), true);
            temp_struct.name = Objects.requireNonNull(faction_section.get("name")).toString();
            temp_struct.description = utils.convertObjectToList(Objects.requireNonNull(faction_section.get("description")));
            temp_struct.balance = (float) faction_section.get("cache.balance");

            struct.add(temp_struct);

        }
    }

    public List<FactionStruct> getStruct(){
        return struct;
    }

    public void setFactionBalance(float balance, int faction_id){

    }
}
