package io.github.cdcon.factions.utility.structs;

public class NpcBehaviorStruct {
    public double npc_uprising_mood = 0; // Если равняется 100 - восстание начинается
    public double npc_uprising_factor = 1; // Скорость, с которой растёт вероятность восстания
    // Это свойство нужно лишь для предотвращения двойных восстаний в фракции
    public boolean is_uprising_active = false;
    public int npc_salary_base = 100;
    public double npc_salary_citizen_multiplier = 0.1;
    public double npc_salary_farmer_multiplier = 1;
    public double npc_salary_guard_multiplier = 1;
    public double npc_salary_woodcutter_multiplier = 1;
    public double npc_salary_miner_multiplier = 1;
}
