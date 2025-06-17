package paymentsystem.security.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import paymentsystem.exception.exceptions.UserNotFoundException;
import paymentsystem.model.entity.UserEntity;
import paymentsystem.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(UserNotFoundException::new);

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userEntity.getRole().name());

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                Collections.singletonList(simpleGrantedAuthority)
        );
    }
}
