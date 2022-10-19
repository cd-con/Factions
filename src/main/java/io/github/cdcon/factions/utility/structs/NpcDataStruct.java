package io.github.cdcon.factions.utility.structs;

import io.github.cdcon.factions.utility.types.NPC;

public class NpcDataStruct {
    private NPC npc_type = NPC.CITIZEN;
    private int npc_id = 0;

    public int getNpc_id(){
        return npc_id;
    }

    public NPC getNpc_type(){
        return npc_type;
    }

    public void setNpc_id(int id){
        npc_id = id;
    }

    public void setNpc_type(NPC type){
        npc_type = type;
    }
}
