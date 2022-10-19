package io.github.cdcon.factions.utility.structs;

import java.util.ArrayList;
import java.util.List;


public class FactionStruct {
    public int id = 0;

    public boolean players_can_join = true;
    public String name;
    public List<?> description = new ArrayList<>();

    public float balance = 0;

    public NpcBehaviorStruct uprisingBehaviorController = new NpcBehaviorStruct();

}
