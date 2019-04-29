package pl.on.full.hack.db;

import lombok.Getter;

@Getter
public enum LeagueType {
    PRIVATE("private"),
    PUBLIC("public");

    private String name;

    LeagueType(String name) {
        this.name = name;
    }
}
