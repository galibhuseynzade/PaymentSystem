package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.UserDto;
import paymentsystem.service.abstraction.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        return userService.getAllUsers(page, size);
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
    public Boolean activateUser(@RequestParam String username) {
        return userService.activateUser(username);
    }

    @PutMapping("/disableUser")
    public Boolean disableUser(@RequestParam String username) {
        return userService.disableUser(username);
    }

    @PutMapping("/changePassword")
    public Boolean changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String oldPassword, @RequestParam String newPassword) {
        String username = userDetails.getUsername();
        return userService.changePassword(username, oldPassword, newPassword);
    }

}
