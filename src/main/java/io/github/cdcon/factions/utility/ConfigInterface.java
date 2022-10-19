package io.github.cdcon.factions.utility;

import io.github.cdcon.factions.Main;


public class ConfigInterface {

    public void defaultConfig(){
        Main.pluginInstance.saveDefaultConfig();
    }

    public void saveConfig(){
        Main.pluginInstance.saveConfig();
    }

}

