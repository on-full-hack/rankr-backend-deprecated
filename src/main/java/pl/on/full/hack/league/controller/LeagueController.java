package pl.on.full.hack.league.controller;

import javassist.NotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.on.full.hack.auth.exception.UnauthorizedException;
import pl.on.full.hack.base.dto.BaseApiContract;
import pl.on.full.hack.league.dto.LeagueDTO;
import pl.on.full.hack.league.dto.LeagueDetailsDTO;
import pl.on.full.hack.league.entity.LeaguePlayerId;
import pl.on.full.hack.league.exception.PendingRequestException;
import pl.on.full.hack.league.service.LeaguePlayerService;
import pl.on.full.hack.league.service.LeagueService;

import java.util.Set;

@RestController
@RequestMapping("/leagues")
@CommonsLog
public class LeagueController {

    private LeagueService leagueService;
    private LeaguePlayerService leaguePlayerService;

    @Autowired
    public LeagueController(LeagueService leagueService, LeaguePlayerService leaguePlayerService) {
        this.leagueService = leagueService;
        this.leaguePlayerService = leaguePlayerService;
    }

    @GetMapping(path = "")
    public ResponseEntity<BaseApiContract<Set<LeagueDTO>>> findAllLeagues() {
        final BaseApiContract<Set<LeagueDTO>> responseBody = new BaseApiContract<>();
        try {
            responseBody.setSpecificContract(leagueService.findAll());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);
        }
    }

    @PostMapping(path = "")
    public ResponseEntity<BaseApiContract<LeagueDTO>> addNewLeague(@RequestBody LeagueDTO leagueDTO, Authentication authentication) {
        final BaseApiContract<LeagueDTO> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            responseBody.setSpecificContract(leagueService.addNewLeague(leagueDTO, username));
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BaseApiContract<LeagueDetailsDTO>> getLeagueDetails(@PathVariable("id") Long leagueId) {
        final BaseApiContract<LeagueDetailsDTO> responseBody = new BaseApiContract<>();
        try {
            responseBody.setSpecificContract(leagueService.getDetails(leagueId));
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (NotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);
        }
    }

    @PutMapping(path = "")
    public ResponseEntity<BaseApiContract<Void>> updateLeague(@RequestBody LeagueDTO leagueDTO, Authentication authentication){
        final BaseApiContract<Void> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            leagueService.updateLeague(leagueDTO, username);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (NotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (UnauthorizedException e){
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseApiContract<Void>> deleteLeague(@PathVariable("id") Long leagueId, Authentication authentication) {
        final BaseApiContract<Void> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            leagueService.deleteLeague(leagueId, username);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (NotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);
        }
    }

    @PostMapping(path = "/user/join/{leagueId}")
    public ResponseEntity<BaseApiContract<Void>> joinToLeague(@PathVariable("leagueId") Long leagueId, Authentication authentication){
        final BaseApiContract<Void> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            leaguePlayerService.joinToLeague(leagueId, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (PendingRequestException e) {
            responseBody.setError("Request is waiting for acceptance");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseBody);
        } catch (Exception e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping(path = "/user/join/link/{leagueCodeToJoin}")
    public ResponseEntity<BaseApiContract<Void>> joinToLeagueByLink(@PathVariable("leagueCodeToJoin") String leagueCodeToJoin, Authentication authentication){
        final BaseApiContract<Void> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            leaguePlayerService.joinToLeagueByLink(leagueCodeToJoin, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (Exception e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

    @PostMapping(path = "/user")
    public ResponseEntity<BaseApiContract<Void>> inviteToLeague(@RequestBody LeaguePlayerId leaguePlayerId, Authentication authentication){
        final BaseApiContract<Void> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            leaguePlayerService.inviteToLeague(leaguePlayerId, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (UnauthorizedException e){
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        } catch (NotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }

    @DeleteMapping(path = "/user/{userId}/league/{leagueId}")
    public ResponseEntity<BaseApiContract<Void>> removeFromLeague(@PathVariable("userId") Long userId, @PathVariable("leagueId") Long leagueId, Authentication authentication){
        final BaseApiContract<Void> responseBody = new BaseApiContract<>();
        try {
            final String username = (String) authentication.getPrincipal();
            final LeaguePlayerId leaguePlayerId = new LeaguePlayerId(userId, leagueId);
            leaguePlayerService.removeFromLeague(leaguePlayerId, username);
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (UnauthorizedException e){
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        } catch (NotFoundException e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }
    }
}
