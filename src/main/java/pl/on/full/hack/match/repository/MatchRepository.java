package pl.on.full.hack.match.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.on.full.hack.league.entity.League;
import pl.on.full.hack.match.entity.Match;

import java.util.List;

@Repository
public interface MatchRepository extends PagingAndSortingRepository<Match, Long> {

    List<Match> findAllByLeague(final League league, final Pageable pageable);
}
