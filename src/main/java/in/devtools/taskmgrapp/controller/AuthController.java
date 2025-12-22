package in.devtools.taskmgrapp.controller;

import in.devtools.taskmgrapp.dto.UserDto;
import in.devtools.taskmgrapp.entity.User;
import in.devtools.taskmgrapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "/users/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
//        User user = new User();
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "/users/register";
    }

    @PostMapping("/register/save")
    public String saveRegistration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model)
    {
        Optional<User> existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            result.rejectValue("email", null, "This mail already registered");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/users/register";
        }
        userService.saveUser(userDto);
        return "redirect:/login?success";

    }

}
