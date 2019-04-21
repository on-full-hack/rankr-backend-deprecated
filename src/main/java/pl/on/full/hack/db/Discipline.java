package pl.on.full.hack.db;

import lombok.Getter;

@Getter
public enum Discipline {
    SQUASH("Squash");

    private String name;

    Discipline(String name) {
        this.name = name;
    }
}
