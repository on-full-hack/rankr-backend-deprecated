package pl.on.full.hack.match.controller;

import javassist.NotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.auth.repository.UserRepository;
import pl.on.full.hack.base.dto.BaseApiContract;
import pl.on.full.hack.db.Venue;
import pl.on.full.hack.league.dto.MatchDTO;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.repository.LeagueRepository;
import pl.on.full.hack.match.Templates.MatchDetails;
import pl.on.full.hack.match.entity.Match;
import pl.on.full.hack.match.repository.MatchRepository;
import pl.on.full.hack.match.service.MatchService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CommonsLog
public class MatchController {

    private MatchService matchService;
    private MatchRepository matchRepository;
    private UserRepository userRepository;
    private LeagueRepository leagueRepository;

    @Autowired
    public MatchController(MatchService matchService, MatchRepository matchRepository, UserRepository userRepository, LeagueRepository leagueRepository) {
        this.matchService = matchService;
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.leagueRepository = leagueRepository;
    }

    @GetMapping(path = "/leagues/{leagueId}/matches")
    public ResponseEntity<BaseApiContract<List<MatchDTO>>> getMatchesPageForLeague(final @PathVariable("leagueId") Long leagueId, final Pageable pageable) {
        final BaseApiContract<List<MatchDTO>> responseBody = new BaseApiContract<>();
        try {
            responseBody.setSpecificContract(matchService.findPageByLeague(leagueId, pageable));
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (NotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);
        }
    }

    @PostMapping(path = "/leagues/{leagueId}/matches")
    public ResponseEntity<?> saveMatchDetails(final @PathVariable("leagueId") Long leagueId, @RequestBody MatchDetails matchDetails) {
        Optional<RankrUser> winner = userRepository.findById(matchDetails.getWinnerId());
        Optional<League> league = leagueRepository.findById(leagueId);
        if (!winner.isPresent() || !league.isPresent())
            return ResponseEntity.unprocessableEntity().body("Error Processing Data");
        Set<RankrUser> players = new HashSet<>();
        for (Long id : matchDetails.getPlayers()) {
            Optional<RankrUser> user = userRepository.findById(id);
            if (user.isPresent())
                players.add(user.get());
            else {
                return ResponseEntity.unprocessableEntity().body("Error Getting Users Data");
            }
        }
        Venue venue = matchDetails.getVenue();
        String discipline = matchDetails.getDiscipline();


        Match match = new Match(venue, winner.get(), discipline, league.get(), players);
        matchRepository.save(match);

        return ResponseEntity.ok("Match Added SuccessFully");
    }

}
