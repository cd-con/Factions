package io.github.cdcon.factions.utility.types;

public class NPC {

    private final short type;
    private double uprising_involvement;
    private NPC(short type, double uprising_involvement) {
        this.type = type;
        this.uprising_involvement = uprising_involvement;
    }

    public static NPC CITIZEN = new NPC(Short.parseShort("0"), 0.225);
    public static NPC FARMER = new NPC(Short.parseShort("1"), 0.15525);
    public static NPC GUARD = new NPC(Short.parseShort("2"), 0.6575);
    public static NPC WOODCUTTER = new NPC(Short.parseShort("3"), 0.4325);
    public static NPC MINER = new NPC(Short.parseShort("4"), 0.4575);
}
