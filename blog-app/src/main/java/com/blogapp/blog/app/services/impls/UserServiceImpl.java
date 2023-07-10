package com.blogapp.blog.app.services.impls;

import com.blogapp.blog.app.config.AppConstants;
import com.blogapp.blog.app.entities.Role;
import com.blogapp.blog.app.entities.User;
import com.blogapp.blog.app.exception.ResourceNotFound;
import com.blogapp.blog.app.exception.SameResourceFound;
import com.blogapp.blog.app.payload.UserDto;
import com.blogapp.blog.app.repositories.RoleRepo;
import com.blogapp.blog.app.repositories.UserRepo;
import com.blogapp.blog.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = dtoToUser(userDto);

        Optional<User> user1  = userRepo.findByEmail(user.getEmail());
        if(user1.isPresent()){
            throw new SameResourceFound("Email already exists");
        }
        //encoded the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //roles
        Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser = userRepo.save(user);


        return userToDto(newUser);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        Optional<User> user1  = userRepo.findByEmail(user.getEmail());
        if(user1.isPresent()){
            throw new SameResourceFound("Email already exists");
        }
        User savedUser = userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId)
                     .orElseThrow(()-> new ResourceNotFound("User","user id",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = userRepo.save(user);

        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFound("User","user id",userId));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDtos;

    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFound("User","user id",userId));
        userRepo.delete(user);

    }

    public User dtoToUser(UserDto userDto){
        User user = modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto = modelMapper.map(user,UserDto.class);
        return userDto;
    }
}
