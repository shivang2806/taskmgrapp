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
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            RoleRepository roleRepository,
            UserRepository userRepository,
            ModelMapper modelMapper
            ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveUser(UserDto userDto)
    {

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = new Role();
            role.setName("USER");
            roleRepository.save(role);
        }
        user.setRoles(Arrays.asList(role));

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


    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user)->modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }


}
