package pl.on.full.hack.base.mapping;

import org.modelmapper.PropertyMap;
import pl.on.full.hack.league.dto.LeagueDTO;
import pl.on.full.hack.league.entity.League;

public class LeagueToDTOMapping extends PropertyMap<League, LeagueDTO>{
    @Override
    protected void configure() {
        map(source.getCreator().getId()).setCreatorId(null);
    }
}
