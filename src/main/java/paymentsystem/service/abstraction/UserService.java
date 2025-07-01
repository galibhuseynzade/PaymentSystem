package paymentsystem.service.abstraction;

import paymentsystem.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createGenericUser(String username, String password);
    UserDto createAdminUser(String username, String password);
    Boolean activateUser(String username);
    Boolean disableUser(String username);
    Boolean changePassword(String username, String oldPassword, String newPassword);
    List<UserDto> getAllUsers(Integer page, Integer size);
}
