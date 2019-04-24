package pl.on.full.hack.league.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.auth.repository.UserRepository;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.entity.LeaguePlayer;
import pl.on.full.hack.league.entity.LeaguePlayerId;
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

    public String joinToLeague(final Long league_id, final String username) throws NotFoundException, Exception{
        if (league_id != null && username != null) {
            final Optional<League> leagueOptional = leagueRepository.findById(league_id);
            final League league = leagueOptional
                    .orElseThrow(() -> new NotFoundException("No league with id " + league_id));

            //TODO: 1. Do we should check if the user is an admin?
            //TODO: 2. Create league -> add new user as admin?

            final RankrUser rankrUser = userRepository.findByUsername(username);
            final LeaguePlayerId leaguePlayerId = new LeaguePlayerId(rankrUser.getId(), league_id);

            Optional<LeaguePlayer> checkIfPlayerExists = repository.findById(leaguePlayerId);
            if(checkIfPlayerExists.isPresent()){ throw new Exception ("Player actually is waiting for accept in league"); }

            LeaguePlayer player = new LeaguePlayer();
            player.setId(leaguePlayerId);
            player.setPlayer(rankrUser);
            player.setLeague(league);
            player.setRating(0L);
            player.setActive(false);
            repository.save(player);
            return "User joined to league successfully";
        } else {
            throw new NullPointerException("leaguePlayerDTO cannot be null");
        }
    }
}
