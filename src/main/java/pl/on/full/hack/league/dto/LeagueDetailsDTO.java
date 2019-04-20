package pl.on.full.hack.league.dto;

import lombok.Data;

import java.util.List;

@Data
public class LeagueDetailsDTO {

    private Long id;

    private String name;

    private String discipline;

    private String description;

    private List<LeaguePlayerDTO> players;

    private List<MatchDTO> matches;
}
