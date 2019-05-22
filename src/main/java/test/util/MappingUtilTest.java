package test.util;

import static org.junit.Assert.*;
import org.junit.Test;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.base.utils.MappingUtil;
import pl.on.full.hack.db.Discipline;
import pl.on.full.hack.match.entity.Match;
import pl.on.full.hack.db.Venue;
import pl.on.full.hack.league.dto.LeaguePlayerDTO;
import pl.on.full.hack.league.dto.MatchDTO;
import pl.on.full.hack.league.entity.LeaguePlayer;
import pl.on.full.hack.league.entity.LeaguePlayerId;

public class MappingUtilTest {

    @Test
    public void testMatchDTOMapping() {
        Match m = prepareMatch();
        MatchDTO matchDTO = MappingUtil.map(m, MatchDTO.class);

        assertEquals(matchDTO.getVenue(), m.getVenue().getName());
        assertEquals(matchDTO.getDiscipline(), m.getDiscipline());
        assertEquals(matchDTO.getWinner(), m.getWinner().getUsername());

    }

    @Test
    public void testLeaguePlayerDTOMapping() {
        LeaguePlayer leaguePlayer = prepareLeaguePlayer();
        LeaguePlayerDTO leaguePlayerDTO = MappingUtil.map(leaguePlayer, LeaguePlayerDTO.class);

        assertEquals(leaguePlayerDTO.getId(), leaguePlayer.getId().getUserId());
        assertEquals(leaguePlayerDTO.getRating(), leaguePlayer.getRating());
        assertEquals(leaguePlayerDTO.getUsername(), leaguePlayer.getPlayer().getUsername());
        assertEquals(leaguePlayerDTO.getActive(), leaguePlayer.getActive());

    }

    private Match prepareMatch() {
        Match match = new Match();
        match.setDiscipline(Discipline.SQUASH.getName());
        match.setVenue(new Venue(1L, "Venue test", 1.1d, 1.5d, null));
        match.setWinner(new RankrUser(0, "pwypchal", null, null, null, null));
        return match;
    }

    private LeaguePlayer prepareLeaguePlayer() {
        LeaguePlayer leaguePlayer = new LeaguePlayer();
        leaguePlayer.setPlayer(new RankrUser(0, "pwypchal", null, null, null, null));
        leaguePlayer.setId(new LeaguePlayerId(1L, 2L));
        leaguePlayer.setRating(100L);
        leaguePlayer.setActive(true);
        return leaguePlayer;
    }

}
