package pl.on.full.hack.league.service;

import javassist.NotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.auth.exception.UnauthorizedException;
import pl.on.full.hack.auth.repository.UserRepository;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.league.entity.LeaguePlayer;
import pl.on.full.hack.league.entity.LeaguePlayerId;
import pl.on.full.hack.league.exception.PendingRequestException;
import pl.on.full.hack.league.repository.LeaguePlayerRepository;
import pl.on.full.hack.league.repository.LeagueRepository;

import java.util.List;
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

    public void joinToLeague(@NonNull final Long league_id,@NonNull final String username) throws NotFoundException, PendingRequestException{
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
    }

    public void joinToLeagueByLink(@NonNull final String leagueCodeToJoin, @NonNull final String username) throws NotFoundException, PendingRequestException{
        final League leagueToJoin = leagueRepository.findByCodeToJoin(leagueCodeToJoin);
        if(leagueToJoin == null){
            throw new NotFoundException("No league with provided link found");
        }

        final League league = leagueToJoin;

        //TODO: 1. Do we should check if the user is an admin?
        //TODO: 2. Create league -> add new user as admin?

        final RankrUser rankrUser = userRepository.findByUsername(username);
        final LeaguePlayerId leaguePlayerId = new LeaguePlayerId(rankrUser.getId(), league.getId());

        LeaguePlayer player = new LeaguePlayer(leaguePlayerId, rankrUser, league, 0L, true);

        repository.save(player);
    }

    public void inviteToLeague(@NonNull final LeaguePlayerId leaguePlayerId, @NonNull final  String username) throws NotFoundException, UnauthorizedException{
        final RankrUser leagueAdmin = userRepository.findByUsername(username);
        final Optional<League> leagueOptional = leagueRepository.findById(leaguePlayerId.getLeagueId());
        final League league = leagueOptional
                .orElseThrow(() -> new NotFoundException("No league with id " + leaguePlayerId.getLeagueId()));

        //TODO: 1. Check all users which has admin access granted
        //TODO: 2. Generate link for league to automatically add by using it
        if(leagueAdmin.getId() != league.getCreator().getId()){
            throw new UnauthorizedException();
        }

        Optional<RankrUser> rankrUserOptional = userRepository.findById(leaguePlayerId.getUserId());
        RankrUser rankrUser = rankrUserOptional
                .orElseThrow(() -> new NotFoundException("No player with id " + leaguePlayerId.getLeagueId()));

        LeaguePlayer newPlayer = new LeaguePlayer(leaguePlayerId, rankrUser, league, 0L, true);
        repository.save(newPlayer);
    }

    public void removeFromLeague(@NonNull final LeaguePlayerId leaguePlayerId, @NonNull final  String username) throws NotFoundException, UnauthorizedException{
        final RankrUser userAuthenticated = userRepository.findByUsername(username);
        final Optional<League> leagueOptional = leagueRepository.findById(leaguePlayerId.getLeagueId());
        final League league = leagueOptional
                .orElseThrow(() -> new NotFoundException("No league with id " + leaguePlayerId.getLeagueId()));

        //TODO: check all users which has admin access granted
        if(leaguePlayerId.getUserId() != userAuthenticated.getId() && userAuthenticated.getId() != league.getCreator().getId()){
            throw new UnauthorizedException();
        }

        Optional<RankrUser> rankrUserOptional = userRepository.findById(leaguePlayerId.getUserId());
        RankrUser rankrUser = rankrUserOptional
                .orElseThrow(() -> new NotFoundException("No player with id " + leaguePlayerId.getLeagueId()));

        LeaguePlayer playerToRemove = new LeaguePlayer(leaguePlayerId, rankrUser, league, 0L, true);

        repository.delete(playerToRemove);
    }
}
