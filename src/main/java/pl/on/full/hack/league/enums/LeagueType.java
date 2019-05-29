package pl.on.full.hack.league.enums;

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
