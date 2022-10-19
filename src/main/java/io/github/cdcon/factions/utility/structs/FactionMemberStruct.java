package io.github.cdcon.factions.utility.structs;

public class FactionMemberStruct {
    public String nickname;

    public boolean is_faction_member;
    public int joined_faction;

    public boolean is_faction_gov_member;
    public int chosen_in_gov;
    public short gov_type;
    public String cause;
    public boolean should_be_demoted_with_new_leader;

    // Dev tags
    public boolean uprising_immune;

}
