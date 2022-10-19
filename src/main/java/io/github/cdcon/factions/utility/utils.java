package io.github.cdcon.factions.utility;

import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class utils {
    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }
    public static List<Component> convertStringToComponent(List<?> list){
        List<Component> output = new ArrayList<>();
        for (Object listline: list) {
            output.add(Component.text(listline.toString()));
        }
        return output;
    }
}
