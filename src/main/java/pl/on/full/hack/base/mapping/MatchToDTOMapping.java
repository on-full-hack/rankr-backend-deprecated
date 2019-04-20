package pl.on.full.hack.base.mapping;

import org.modelmapper.PropertyMap;
import pl.on.full.hack.db.Match;
import pl.on.full.hack.league.dto.MatchDTO;

public class MatchToDTOMapping extends PropertyMap<Match, MatchDTO> {

    @Override
    protected void configure() {
        map(source.getDiscipline()).setDiscipline(null);
        map(source.getVenue().getName()).setVenue(null);
        map(source.getWinner().getUsername()).setWinner(null);
    }
}
