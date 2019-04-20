package pl.on.full.hack.league.dto;

import lombok.Data;

@Data
public class LeaguePlayerIdDTO {
    private Long id;

    private Long leagueId;

    private Long userId;

    private boolean activated;
}
