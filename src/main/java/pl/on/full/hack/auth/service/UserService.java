package pl.on.full.hack.auth.service;

import javassist.NotFoundException;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.on.full.hack.auth.dto.UserDTO;
import pl.on.full.hack.auth.entity.RankrUser;
import pl.on.full.hack.auth.exception.UserAlreadyExistsException;
import pl.on.full.hack.auth.repository.UserRepository;
import pl.on.full.hack.base.utils.MappingUtil;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        RankrUser rankrUser = userRepository.findByUsername(username);
        if (rankrUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(rankrUser.getUsername(), rankrUser.getPassword(), emptyList());
    }

    public void signUp(UserDTO user) {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(MappingUtil.map(user, RankrUser.class));
        } else {
            throw new UserAlreadyExistsException();
        }
    }

    public UserDTO getDetails(@NonNull String username) throws UsernameNotFoundException {
        RankrUser rankrUser = userRepository.findByUsername(username);
        if (rankrUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return MappingUtil.map(rankrUser, UserDTO.class);
    }
}