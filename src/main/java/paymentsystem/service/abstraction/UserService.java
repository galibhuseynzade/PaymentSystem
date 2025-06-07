package paymentsystem.service.abstraction;

import org.springframework.data.domain.Page;
import paymentsystem.model.dto.UserDto;

public interface UserService {
    UserDto createGenericUser(String username, String password);
    UserDto createAdminUser(String username, String password);
    void activateUser(String username);
    void disableUser(String username);
    void changePassword(String username, String oldPassword, String newPassword);
    Page<UserDto> getAllUsers(Integer page, Integer size);
}
