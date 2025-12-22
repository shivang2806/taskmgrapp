package in.devtools.taskmgrapp.service.impl;

import in.devtools.taskmgrapp.dto.UserDto;
import in.devtools.taskmgrapp.entity.Role;
import in.devtools.taskmgrapp.entity.User;
import in.devtools.taskmgrapp.repository.RoleRepository;
import in.devtools.taskmgrapp.repository.UserRepository;
import in.devtools.taskmgrapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(UserDto userDto)
    {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER");
        if(role==null){
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role)); // ✅ correct
        userRepository.save(user);

    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }



}
