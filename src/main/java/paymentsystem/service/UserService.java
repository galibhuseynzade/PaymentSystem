package paymentsystem.service;

import paymentsystem.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createGenericUser(String username, String password);
    UserDto createAdminUser(String username, String password);
    String activateUser(String username);
    String disableUser(String username);
    String changePassword(String username, String oldPassword, String newPassword);
    List<UserDto> findAllUsers();
}
