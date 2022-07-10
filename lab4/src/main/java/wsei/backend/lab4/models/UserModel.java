package wsei.backend.lab4.models;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data  
@NoArgsConstructor
@Getter
@Setter

public class UserModel {
    @Nullable
    private String Username;
    @Nullable
    private String Email;
    @Nullable
    private String FirstName;
    @Nullable
    private String LastName;
    @Nullable
    private String Password;
    @Nullable
    private String NewPassword;
}
