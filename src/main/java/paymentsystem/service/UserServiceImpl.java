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
import paymentsystem.model.enums.UserRole;
import paymentsystem.model.enums.UserStatus;
import paymentsystem.repository.UserRepository;

import java.time.LocalDate;
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

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .role(UserRole.USER)
                .registrationDate(LocalDate.now())
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(userEntity);
        log.info("User created " + username);
        return userMapper.mapToUserDto(userEntity);
    }

    @Override
    public UserDto createAdminUser(String username, String password) {
        if (userRepository.existsById(username))
            throw new UserAlreadyExistsException();

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .role(UserRole.ADMIN)
                .registrationDate(LocalDate.now())
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(userEntity);
        log.info("User created " + username);
        return userMapper.mapToUserDto(userEntity);
    }

    @Override
    public String activateUser(String username) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        UserEntity userEntity = userRepository.getReferenceById(username);
        if (userEntity.getStatus().equals(UserStatus.ACTIVE))
            throw new UserAlreadyActiveException();

        userEntity.setStatus(UserStatus.ACTIVE);
        userRepository.save(userEntity);
        return "User activated successfully";
    }

    @Override
    public String disableUser(String username) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        UserEntity userEntity = userRepository.getReferenceById(username);
        if (userEntity.getStatus().equals(UserStatus.DISABLED))
            throw new UserAlreadyDisabledException();

        userEntity.setStatus(UserStatus.DISABLED);
        userRepository.save(userEntity);
        return "User disabled successfully";
    }

    @Override
    public String changePassword(String username, String oldPassword, String newPassword) {
        if (!userRepository.existsById(username))
            throw new UserNotFoundException();

        if (oldPassword.equals(newPassword))
            throw new PasswordsMatchException();

        UserEntity userEntity = userRepository.getReferenceById(username);
        if (!userEntity.getPassword().equals(oldPassword))
            throw new IncorrectPasswordException();

        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);
        return "Password changed successfully";
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> userEntityList = userRepository.findByStatus(UserStatus.ACTIVE);
        return userEntityList.stream().map(userMapper::mapToUserDto).collect(Collectors.toList());
    }
}

