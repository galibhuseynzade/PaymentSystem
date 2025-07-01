package paymentsystem.service.concrete;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import paymentsystem.exception.exceptions.IncorrectPasswordException;
import paymentsystem.exception.exceptions.PasswordsMatchException;
import paymentsystem.exception.exceptions.UserAlreadyActiveException;
import paymentsystem.exception.exceptions.UserAlreadyDisabledException;
import paymentsystem.exception.exceptions.UserAlreadyExistsException;
import paymentsystem.exception.exceptions.UserNotFoundException;
import paymentsystem.mapper.UserMapper;
import paymentsystem.model.dto.UserDto;
import paymentsystem.model.entity.UserEntity;
import paymentsystem.model.enums.UserRole;
import paymentsystem.model.enums.UserStatus;
import paymentsystem.repository.UserRepository;
import paymentsystem.service.abstraction.UserService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserDto createGenericUser(String username, String password) {
        if (userRepository.existsById(username))
            throw new UserAlreadyExistsException();

        String encodedPassword = passwordEncoder.encode(password);
        UserEntity userEntity = userMapper.buildUserEntity(username, encodedPassword, UserRole.USER);

        userRepository.save(userEntity);
        log.info("User created " + username);
        return userMapper.mapToUserDto(userEntity);
    }

    @Override
    public UserDto createAdminUser(String username, String password) {
        if (userRepository.existsById(username))
            throw new UserAlreadyExistsException();

        String encodedPassword = passwordEncoder.encode(password);
        UserEntity userEntity = userMapper.buildUserEntity(username, encodedPassword, UserRole.ADMIN);

        userRepository.save(userEntity);
        log.info("User created " + username);
        return userMapper.mapToUserDto(userEntity);
    }

    @Override
    public Boolean activateUser(String username) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        UserEntity userEntity = userRepository.getReferenceById(username);
        if (userEntity.getStatus().equals(UserStatus.ACTIVE))
            throw new UserAlreadyActiveException();

        userEntity.setStatus(UserStatus.ACTIVE);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public Boolean disableUser(String username) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        UserEntity userEntity = userRepository.getReferenceById(username);
        if (userEntity.getStatus().equals(UserStatus.DISABLED))
            throw new UserAlreadyDisabledException();

        userEntity.setStatus(UserStatus.DISABLED);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public Boolean changePassword(String username, String oldPassword, String newPassword) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        UserEntity userEntity = userRepository.getReferenceById(username);

        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword()))
            throw new IncorrectPasswordException();

        if (passwordEncoder.matches(newPassword, userEntity.getPassword()))
            throw new PasswordsMatchException();

        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public List<UserDto> getAllUsers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserEntity> userEntityPage = userRepository.findByStatus(UserStatus.ACTIVE, pageable);
        List<UserDto> userDtoList = userEntityPage.getContent().stream().map(userMapper::mapToUserDto).toList();
        return new PageImpl<>(userDtoList, pageable, userEntityPage.getTotalElements()).getContent();
    }
}

