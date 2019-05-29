package pl.on.full.hack.match.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.on.full.hack.league.dto.MatchDTO;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.repository.LeagueRepository;
import pl.on.full.hack.match.entity.Match;
import pl.on.full.hack.match.repository.MatchRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private MatchRepository matchRepository;

    private LeagueRepository leagueRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, LeagueRepository leagueRepository) {
        this.matchRepository = matchRepository;
        this.leagueRepository = leagueRepository;
    }

    public List<MatchDTO> findPageByLeague(final Long leagueId, final Pageable pageable) throws NotFoundException {
        Optional<League> queryResult = leagueRepository.findById(leagueId);
        final League league = queryResult
                .orElseThrow(() -> new NotFoundException("No league with id " + leagueId));

        return matchRepository.findAllByLeague(league, pageable).stream().map(Match::getMatchDTO).collect(Collectors.toList());
    }
}
