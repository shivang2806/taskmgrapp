package in.devtools.taskmgrapp.controller;

import in.devtools.taskmgrapp.dto.UserDto;
import in.devtools.taskmgrapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {


    private UserService userService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users")
    public String getAllUser(Model model)
    {
        List<UserDto> users =  userService.getAllUser();
        model.addAttribute("users", users);

        return "users/userList";
    }

}
