package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.UserDto;
import paymentsystem.model.entity.UserEntity;
import paymentsystem.model.enums.UserRole;
import paymentsystem.model.enums.UserStatus;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity mapToUserEntity(UserDto userDto);

    UserDto mapToUserDto(UserEntity userEntity);

    default UserEntity buildUserEntity(String username, String password) {
        return UserEntity.builder()
                .username(username)
                .password(password)
                .role(UserRole.USER)
                .registrationDate(LocalDate.now())
                .status(UserStatus.ACTIVE)
                .build();
    }
}
