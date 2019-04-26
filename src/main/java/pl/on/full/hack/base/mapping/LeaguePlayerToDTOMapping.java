package pl.on.full.hack.base.mapping;

import org.modelmapper.PropertyMap;
import pl.on.full.hack.league.dto.LeaguePlayerDTO;
import pl.on.full.hack.league.entity.LeaguePlayer;

public class LeaguePlayerToDTOMapping extends PropertyMap<LeaguePlayer, LeaguePlayerDTO> {

    @Override
    protected void configure() {
        map(source.getPlayer().getUsername()).setUsername(null);
        map(source.getRating()).setRating(null);
        map(source.getId().getUserId()).setId(null);
        map(source.getActive()).setActive(null);
    }
}
