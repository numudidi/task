package task.manager.task.authmodule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.manager.task.authmodule.dtos.UserDto;
import task.manager.task.authmodule.dtos.payload.request.RegisterUserPayload;
import task.manager.task.authmodule.dtos.payload.request.ResetPasswordPayload;
import task.manager.task.authmodule.model.UserEntity;
import task.manager.task.authmodule.service.AuthService;
import task.manager.task.model.dto.GenericResponse;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<GenericResponse> registerCustomer(@RequestBody RegisterUserPayload customerPayloadDto){
        UserDto newCustomer = authService.registerUser(customerPayloadDto);
        return new ResponseEntity<>(GenericResponse.builder()
                .message("Your account was created Successfully, Email was sent to your inbox.")
                .status(HttpStatus.CREATED)
                .data(newCustomer)
                .build(), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<GenericResponse> updateUser(@RequestBody RegisterUserPayload customerPayloadDto){
        UserEntity updatedUser = authService.getUserDetails(customerPayloadDto.getEmail());
        UserDto userDto = null;
        if(updatedUser != null){
            if(customerPayloadDto.getFirstName() != null || customerPayloadDto.getFirstName() != ""){
                updatedUser.setFirstName(customerPayloadDto.getFirstName());
            }
            if(customerPayloadDto.getLastName() != null || customerPayloadDto.getLastName() != ""){
                updatedUser.setLastName(customerPayloadDto.getLastName());
            }
            userDto = authService.updateUser(updatedUser);
        }
        return new ResponseEntity<>(GenericResponse.builder()
                .message("Your profile has been updated Successfully")
                .status(HttpStatus.OK)
                .data(userDto)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GenericResponse> resetPassword(@RequestBody ResetPasswordPayload resetPasswordPayload, Principal principal){
        UserEntity userEntity = authService.getUserDetails(principal.getName());
        authService.resetPassword(resetPasswordPayload, userEntity);
        return new ResponseEntity<>(GenericResponse.builder()
                .message("Your password was updated Successfully")
                .status(HttpStatus.OK)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<GenericResponse> geUsers(@PathVariable String userId){
        UserDto user = authService.findUser(userId);
        return new ResponseEntity<>(GenericResponse.builder()
                .message("User was fetched Successfully")
                .status(HttpStatus.OK)
                .data(user)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/all/customers")
    public ResponseEntity<GenericResponse> getAllCustomers(){
        List<UserDto> allCustomers = authService.getAllUsers();
        return new ResponseEntity<>(GenericResponse.builder()
                .message("All customers was fetched Successfully")
                .status(HttpStatus.OK)
                .data(allCustomers)
                .build(), HttpStatus.OK);
    }


    @PostMapping("/testing")
    public ResponseEntity<GenericResponse> getTesting(Principal principal){

        UserEntity user = authService.getUserDetails(principal.getName());


        return new ResponseEntity<>(GenericResponse.builder()
                .message("Your account was created Successfully, Email was sent to your inbox.")
                .status(HttpStatus.CREATED)
                .data(user)
                .build(), HttpStatus.CREATED);
    }
}
