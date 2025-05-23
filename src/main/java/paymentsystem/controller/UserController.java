package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.UserDto;
import paymentsystem.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
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
    public String activateUser(@RequestParam String username) {
        return userService.activateUser(username);
    }

    @PutMapping("/disableUser")
    public String disableUser(@RequestParam String username) {
        return userService.disableUser(username);
    }

    @PutMapping("/changePassword")
    public String changePassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return userService.changePassword(username, oldPassword, newPassword);
    }

}
