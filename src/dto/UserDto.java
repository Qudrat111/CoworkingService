package dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class UserDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private boolean isAdmin;
}
