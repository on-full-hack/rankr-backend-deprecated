package pl.on.full.hack.league.service;

import javassist.NotFoundException;
import lombok.NonNull;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.on.full.hack.auth.exception.UnauthorizedException;
import pl.on.full.hack.auth.repository.UserRepository;
import pl.on.full.hack.base.utils.MappingUtil;
import pl.on.full.hack.league.dto.LeagueDTO;
import pl.on.full.hack.league.dto.LeagueDetailsDTO;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.repository.LeagueRepository;

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private LeagueRepository repository;

    private UserRepository userRepository;

    @Autowired
    public LeagueService(LeagueRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Set<LeagueDTO> findAll() {
        final List<League> leagues = repository.findAll();
        return leagues.stream()
                .map(league -> MappingUtil.map(league, LeagueDTO.class))
                .collect(Collectors.toSet());
    }

    public LeagueDTO addNewLeague(@NonNull final LeagueDTO leagueDTO, final String username) {
        final League league = MappingUtil.map(leagueDTO, League.class);
        league.setCreator(userRepository.findByUsername(username));
        league.setCodeToJoin(this.generateUniqueCodeToJoin());
        repository.save(league);
        return MappingUtil.map(league, LeagueDTO.class);
    }

    public LeagueDetailsDTO getDetails(@NonNull final Long id) throws NotFoundException {
        final Optional<League> leagueOptional = repository.findById(id);
        final League league = leagueOptional
                .orElseThrow(() -> new NotFoundException("No league with id " + id));
        return league.getDetailsDTO();
    }

    public void deleteLeague(@NonNull final Long id, final String username) throws NotFoundException {
        final Optional<League> leagueOptional = repository.findById(id);
        final League league = leagueOptional
                .orElseThrow(() -> new NotFoundException("No league with id " + id));

        if (!league.getCreator().getUsername().equals(username)) {
            throw new UnauthorizedException();
        }

        repository.deleteById(id);
    }

    public void updateLeague(@NonNull LeagueDTO leagueDTO, final String username) throws NotFoundException, UnauthorizedException {
        final Optional<League> leagueOptional = repository.findById(leagueDTO.getId());
        final League league = leagueOptional
                .orElseThrow(() -> new NotFoundException("No league with id " + leagueDTO.getId()));

        if (!league.getCreator().getUsername().equals(username)) {
            throw new UnauthorizedException();
        }

        League updatedLeague = MappingUtil.map(leagueDTO, League.class);
        updatedLeague.setCreator(league.getCreator());
        repository.save(updatedLeague);
    }

    private String generateUniqueCodeToJoin(){
        String generatedString = "";
        while (generatedString.equals("")){
            generatedString = RandomStringUtils.randomAlphabetic(5);
            if (this.repository.findByCodeToJoin(generatedString) != null){
                generatedString = "";
            }
        }
        return generatedString;
    }
}
