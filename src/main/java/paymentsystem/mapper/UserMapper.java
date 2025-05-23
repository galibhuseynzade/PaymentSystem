package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.UserDto;
import paymentsystem.model.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity mapToUserEntity(UserDto userDto);
    UserDto mapToUserDto(UserEntity userEntity);
}
