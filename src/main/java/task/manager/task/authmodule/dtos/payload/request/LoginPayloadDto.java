package task.manager.task.authmodule.dtos.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginPayloadDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
