package pl.on.full.hack.league.service;

import javassist.NotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.auth.repository.UserRepository;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.entity.LeaguePlayer;
import pl.on.full.hack.league.entity.LeaguePlayerId;
import pl.on.full.hack.league.exception.PendingRequestException;
import pl.on.full.hack.league.repository.LeaguePlayerRepository;
import pl.on.full.hack.league.repository.LeagueRepository;

import java.util.Optional;

@Service
public class LeaguePlayerService {

    private LeaguePlayerRepository repository;
    private LeagueRepository leagueRepository;
    private UserRepository userRepository;

    @Autowired
    public LeaguePlayerService (LeaguePlayerRepository repository, UserRepository userRepository, LeagueRepository leagueRepository){
        this.repository = repository;
        this.userRepository = userRepository;
        this.leagueRepository = leagueRepository;
    }

    public String joinToLeague(@NonNull final Long league_id,@NonNull final String username) throws NotFoundException, PendingRequestException{
        final Optional<League> leagueOptional = leagueRepository.findById(league_id);
        final League league = leagueOptional
                .orElseThrow(() -> new NotFoundException("No league with id " + league_id));

        //TODO: 1. Do we should check if the user is an admin?
        //TODO: 2. Create league -> add new user as admin?

        final RankrUser rankrUser = userRepository.findByUsername(username);
        final LeaguePlayerId leaguePlayerId = new LeaguePlayerId(rankrUser.getId(), league_id);

        Optional<LeaguePlayer> checkIfPlayerExists = repository.findById(leaguePlayerId);
        if(checkIfPlayerExists.isPresent()){ throw new PendingRequestException(); }

        LeaguePlayer player = new LeaguePlayer(leaguePlayerId, rankrUser, league, 0L, false);

        repository.save(player);
        return "User joined to league successfully";
    }
}
