package paymentsystem.service;

import paymentsystem.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createGenericUser(String username, String password);
    UserDto createAdminUser(String username, String password);
    void activateUser(String username);
    void disableUser(String username);
    void changePassword(String username, String oldPassword, String newPassword);
    List<UserDto> getAllUsers();
}
