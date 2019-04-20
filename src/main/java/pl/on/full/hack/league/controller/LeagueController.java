package pl.on.full.hack.league.controller;

import javassist.NotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.on.full.hack.base.dto.BaseApiContract;
import pl.on.full.hack.league.dto.LeagueDTO;
import pl.on.full.hack.league.dto.LeagueDetailsDTO;
import pl.on.full.hack.league.service.LeagueService;

import java.util.Set;

@RestController
@RequestMapping("/leagues")
@CommonsLog
public class LeagueController {

    private LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<BaseApiContract<Set<LeagueDTO>>> findAllLeagues() {
        final BaseApiContract<Set<LeagueDTO>> responseBody = new BaseApiContract<>();
        try {
            responseBody.setSpecificContract(leagueService.findAll());
            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (Exception e) {
            return BaseApiContract.internalServerError(e);

        }
    }

    @PostMapping(path = "/")
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

    @PostMapping(path = "/{id}")
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
}
