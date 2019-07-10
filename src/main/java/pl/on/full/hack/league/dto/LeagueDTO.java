package pl.on.full.hack.league.dto;

import lombok.Data;
import pl.on.full.hack.auth.entity.RankrUser;

@Data
public class LeagueDTO {

    private Long id;

    private String name;

    private String discipline;

    private String description;

    private String type;

    private String codeToJoin;

    private Long creatorId;
}
