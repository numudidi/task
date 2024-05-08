package task.manager.task.authmodule.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import task.manager.task.authmodule.dtos.UserDto;
import task.manager.task.authmodule.dtos.payload.request.RegisterUserPayload;
import task.manager.task.authmodule.dtos.payload.request.ResetPasswordPayload;
import task.manager.task.authmodule.model.UserEntity;

import java.util.List;

public interface AuthService extends UserDetailsService {
    public UserDto registerUser(RegisterUserPayload registerUserPayload);

    public UserEntity getUserDetails(String email);
    List<UserDto> getAllUsers();
    public UserDto updateUser(UserEntity user);
    public void resetPassword(ResetPasswordPayload resetPasswordPayload, UserEntity userEntity);
    UserDto findUser(String userId);
}
