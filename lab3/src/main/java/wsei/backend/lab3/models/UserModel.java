package wsei.backend.lab3.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data  
@NoArgsConstructor
@Getter
@Setter

public class UserModel {
    private String Username;
    private String Email;
    private String FirstName;
    private String LastName;
    private String Password;
    private String NewPassword;
}
