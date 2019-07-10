package pl.on.full.hack.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.on.full.hack.auth.entity.RankrUser;

@Repository
public interface UserRepository extends JpaRepository<RankrUser, Long> {

    RankrUser findByUsername(String username);

}
