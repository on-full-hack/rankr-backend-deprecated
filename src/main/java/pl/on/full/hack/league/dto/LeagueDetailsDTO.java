package pl.on.full.hack.league.dto;

import lombok.Data;

import java.util.Set;

@Data
public class LeagueDetailsDTO {

    private Long id;

    private String name;

    private Set<PlayerDTO> players;

    private Set<MatchDTO> matches;
}
