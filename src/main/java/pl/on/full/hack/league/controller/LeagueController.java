package pl.on.full.hack.league.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.on.full.hack.base.dto.BaseApiContract;
import pl.on.full.hack.league.dto.LeagueDTO;
import pl.on.full.hack.league.service.LeagueService;

import java.util.Set;

@RestController
@RequestMapping("/leagues")
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
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
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
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
