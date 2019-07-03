package pl.on.full.hack.elo.service;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import pl.on.full.hack.league.service.LeaguePlayerService;
import pl.on.full.hack.match.entity.Match;

@Service
public class RatingService {

    private LeaguePlayerService leaguePlayerService;

    public RatingService(LeaguePlayerService leaguePlayerService) {
        this.leaguePlayerService = leaguePlayerService;
    }

    public void updateRating(final Match match) throws NotFoundException {
        final Float winnerRating = leaguePlayerService.getRatingForUser(match.getWinner());
        final Float loserRating = leaguePlayerService.getRatingForUser(match.getLoser());
    }

    private float winProbability(float rating1, float rating2) {
        return 1.0f * 1.0f / (1 + 1.0f *
                (float)(Math.pow(10, 1.0f *
                        (rating1 - rating2) / 400)));
    }
}
