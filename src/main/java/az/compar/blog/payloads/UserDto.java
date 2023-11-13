package az.compar.blog.payloads;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4, message = "Username must be min of 4 characters")
    private String name;
    @Email(message = "Email address not valid.")
    private String email;
    @NotEmpty
    @Size(min = 6, max = 30, message = "Password must be min of 6 and max of 60 chars.")
    private String password;
    @NotEmpty
    private String about;
}
