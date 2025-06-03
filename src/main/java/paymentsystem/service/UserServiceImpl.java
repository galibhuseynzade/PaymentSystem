package paymentsystem.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
import paymentsystem.model.enums.UserStatus;
import paymentsystem.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserDto createGenericUser(String username, String password) {
        if (userRepository.existsById(username))
            throw new UserAlreadyExistsException();

        UserEntity userEntity = userMapper.buildUserEntity(username, password);

        userRepository.save(userEntity);
        log.info("User created " + username);
        return userMapper.mapToUserDto(userEntity);
    }

    @Override
    public UserDto createAdminUser(String username, String password) {
        if (userRepository.existsById(username))
            throw new UserAlreadyExistsException();

        UserEntity userEntity = userMapper.buildUserEntity(username, password);

        userRepository.save(userEntity);
        log.info("User created " + username);
        return userMapper.mapToUserDto(userEntity);
    }

    @Override
    public void activateUser(String username) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        UserEntity userEntity = userRepository.getReferenceById(username);
        if (userEntity.getStatus().equals(UserStatus.ACTIVE))
            throw new UserAlreadyActiveException();

        userEntity.setStatus(UserStatus.ACTIVE);
        userRepository.save(userEntity);
    }

    @Override
    public void disableUser(String username) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        UserEntity userEntity = userRepository.getReferenceById(username);
        if (userEntity.getStatus().equals(UserStatus.DISABLED))
            throw new UserAlreadyDisabledException();

        userEntity.setStatus(UserStatus.DISABLED);
        userRepository.save(userEntity);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        if (oldPassword.equals(newPassword))
            throw new PasswordsMatchException();

        UserEntity userEntity = userRepository.getReferenceById(username);
        if (!userEntity.getPassword().equals(oldPassword))
            throw new IncorrectPasswordException();

        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findByStatus(UserStatus.ACTIVE);
        return userEntityList.stream().map(userMapper::mapToUserDto).collect(Collectors.toList());
    }
}

