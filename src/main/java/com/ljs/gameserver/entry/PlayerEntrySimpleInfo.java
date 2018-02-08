package com.ljs.gameserver.entry;

public class PlayerEntrySimpleInfo {

    private String id;
    private String name;


    public PlayerEntrySimpleInfo setId(String id) {
        this.id = id;
        return this;
    }

    public PlayerEntrySimpleInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
