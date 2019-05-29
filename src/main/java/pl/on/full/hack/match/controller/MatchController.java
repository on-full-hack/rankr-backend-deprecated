package pl.on.full.hack.match.controller;

import javassist.NotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.on.full.hack.base.dto.BaseApiContract;
import pl.on.full.hack.league.dto.MatchDTO;
import pl.on.full.hack.match.service.MatchService;

import java.util.List;

@RestController
@CommonsLog
public class MatchController {

    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
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
}
