package pl.on.full.hack.auth.service;

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

import static java.util.Collections.emptyList;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RankrUser rankrUser = userRepository.findByUsername(username);
        if (rankrUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(rankrUser.getUsername(), rankrUser.getPassword(), emptyList());
    }

    public void signUp(UserDTO user) throws UserAlreadyExistsException {
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(modelMapper.map(user, RankrUser.class));
        } else {
            throw new UserAlreadyExistsException();
        }
    }
}