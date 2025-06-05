package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.UserDto;
import paymentsystem.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    public Page<UserDto> getAllUsers(@RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }

    @PostMapping("/createGenericUser")
    public UserDto createGenericUser(@RequestParam String username, @RequestParam String password) {
        return userService.createGenericUser(username, password);
    }

    @PostMapping("/createAdminUser")
    public UserDto createAdminUser(@RequestParam String username, @RequestParam String password) {
        return userService.createAdminUser(username, password);
    }

    @PutMapping("/activateUser")
    public void activateUser(@RequestParam String username) {
        userService.activateUser(username);
    }

    @PutMapping("/disableUser")
    public void disableUser(@RequestParam String username) {
        userService.disableUser(username);
    }

    @PutMapping("/changePassword")
    public void changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

}
