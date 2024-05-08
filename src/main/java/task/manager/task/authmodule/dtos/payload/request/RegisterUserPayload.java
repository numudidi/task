package task.manager.task.authmodule.dtos.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserPayload {

    @NotBlank(message="First Name is required")
    private String firstName;

    @NotBlank(message="Last Name is required")
    private String lastName;

    @NotBlank(message="The email is required")
    @Email
    private String email;

    @NotBlank(message="The password is required")
    private String password;
}
