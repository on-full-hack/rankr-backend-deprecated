package pl.on.full.hack.base.mapping;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import pl.on.full.hack.auth.dto.UserDTO;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.entity.LeaguePlayer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class RankrUserToUserDTOMapping extends PropertyMap<RankrUser, UserDTO> {

    Converter<Set<League>, List<Long>> createdLeaguestoListOfId = new AbstractConverter<Set<League>, List<Long>>() {
        @Override
        protected List<Long> convert(Set<League> leagues) {
            return leagues.stream().map(league -> league.getId()).collect(Collectors.toList());
        }
    };

    Converter<Set<LeaguePlayer>, List<Long>> joinedLeaguesToListOfId = new AbstractConverter<Set<LeaguePlayer>, List<Long>>() {
        @Override
        protected List<Long> convert(Set<LeaguePlayer> leaguePlayers) {
            return leaguePlayers.stream().map(leaguePlayer -> leaguePlayer.getLeague().getId()).collect(Collectors.toList());
        }
    };

    @Override
    protected void configure() {
        using(createdLeaguestoListOfId).map(source.getCreatedLeagues()).setCreatedLeagues(null);
        using(joinedLeaguesToListOfId).map(source.getLeaguePlayer()).setJoinedLeagues(null);
    }

}
