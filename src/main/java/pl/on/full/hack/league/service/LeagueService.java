package pl.on.full.hack.league.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.on.full.hack.base.utils.MappingUtil;
import pl.on.full.hack.league.dto.LeagueDTO;
import pl.on.full.hack.league.dto.LeagueDetailsDTO;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.repository.LeagueRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private LeagueRepository repository;

    @Autowired
    public LeagueService(LeagueRepository repository) {
        this.repository = repository;
    }

    public Set<LeagueDTO> findAll() {
        final List<League> leagues = repository.findAll();
        return leagues.stream()
                .map(league -> MappingUtil.map(league, LeagueDTO.class))
                .collect(Collectors.toSet());
        }

    public LeagueDTO addNewLeague(final LeagueDTO leagueDTO, final String username) {
        if (leagueDTO != null) {
            final League league = MappingUtil.map(leagueDTO, League.class);
            repository.save(league);
            return MappingUtil.map(league, LeagueDTO.class);
        } else {
            throw new NullPointerException("LeagueDTO cannot be null");
        }
    }

    public LeagueDetailsDTO getDetails(final Long id) throws NotFoundException {
        final Optional<League> leagueOptional = repository.findById(id);
        final League league = leagueOptional
                .orElseThrow(() -> new NotFoundException("No league with id " + id));

        return  league.getDetailsDTO();
    }
}
