package pl.on.full.hack.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.auth.repository.UserRepository;

import static java.util.Collections.emptyList;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RankrUser rankrUser = userRepository.findByUsername(username);
        if (rankrUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(rankrUser.getUsername(), rankrUser.getPassword(), emptyList());
    }
}