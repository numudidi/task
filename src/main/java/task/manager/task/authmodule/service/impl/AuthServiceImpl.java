package task.manager.task.authmodule.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import task.manager.task.authmodule.dtos.UserDto;
import task.manager.task.authmodule.dtos.payload.request.RegisterUserPayload;
import task.manager.task.authmodule.dtos.payload.request.ResetPasswordPayload;
import task.manager.task.authmodule.exceptions.UserExistsException;
import task.manager.task.authmodule.exceptions.UserNotFoundException;
import task.manager.task.authmodule.model.UserEntity;
import task.manager.task.authmodule.repository.UsersRepository;
import task.manager.task.authmodule.service.AuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto registerUser(RegisterUserPayload registerUserPayload) {

        if(usersRepository.findUserEntitiesByEmail(registerUserPayload.getEmail()).isPresent())
                        throw new UserExistsException("User with the same email already exists!");

        UserEntity user = new ModelMapper().map(registerUserPayload, UserEntity.class);

        user.setPassword(bCryptPasswordEncoder.encode(registerUserPayload.getPassword()));
        user.setUserId(UUID.randomUUID().toString());


        return new ModelMapper().map(usersRepository.save(user), UserDto.class);
    }

    @Override
    public UserEntity getUserDetails(String email) {
       return usersRepository.findUserEntitiesByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found!"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return Streamable.of(usersRepository.findAll()).toList().stream().map(user -> new ModelMapper().map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userEntity = usersRepository.findUserEntitiesByEmail(username);
        if(userEntity.isEmpty()) throw new UsernameNotFoundException(username);


        return new org.springframework.security.core.userdetails.User(userEntity.get().getEmail(),
                userEntity.get().getPassword(),
                true,
                true,
                true,
                true, new ArrayList<>());
    }

    @Override
    public UserDto updateUser(UserEntity user) {
        return new ModelMapper().map(usersRepository.save(user), UserDto.class);
    }

    @Override
    public void resetPassword(ResetPasswordPayload resetPasswordPayload, UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(resetPasswordPayload.getPassword()));
        usersRepository.save(userEntity);
    }

    @Override
    public UserDto findUser(String userId) {
        return new ModelMapper().map(usersRepository.findById(userId).get(), UserDto.class);
    }

}
