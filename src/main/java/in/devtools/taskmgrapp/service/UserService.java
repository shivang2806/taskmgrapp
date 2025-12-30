package in.devtools.taskmgrapp.service;

import in.devtools.taskmgrapp.dto.UserDto;
import in.devtools.taskmgrapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void saveUser(UserDto userDto);
    Optional<User> findUserByEmail(String email);
    List<UserDto> getAllUser();


}
