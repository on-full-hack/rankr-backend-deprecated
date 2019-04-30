package pl.on.full.hack.league.entity;

import lombok.Data;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.base.utils.MappingUtil;
import pl.on.full.hack.db.Match;
import pl.on.full.hack.league.dto.LeagueDetailsDTO;
import pl.on.full.hack.league.dto.LeaguePlayerDTO;
import pl.on.full.hack.league.dto.MatchDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "leagues")
@Data
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String discipline;

    private String type;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private RankrUser creator;

    @OneToMany(mappedBy = "league")
    private Set<Match> matches = new HashSet<>();

    @OneToMany(mappedBy = "league")
    private Set<LeaguePlayer> leaguePlayers = new HashSet<>();

    public LeagueDetailsDTO getDetailsDTO() {
        final LeagueDetailsDTO leagueDetailsDTO = new LeagueDetailsDTO();
        leagueDetailsDTO.setMatches(MappingUtil.mapCollection(this.getMatches(), MatchDTO.class));
        leagueDetailsDTO.setPlayers(MappingUtil.mapCollection(this.getLeaguePlayers(), LeaguePlayerDTO.class));
        leagueDetailsDTO.setDiscipline(getDiscipline());
        leagueDetailsDTO.setName(getName());
        leagueDetailsDTO.setId(getId());
        leagueDetailsDTO.setDescription(getDescription());
        leagueDetailsDTO.setType(getType());
        return leagueDetailsDTO;
    }
}
