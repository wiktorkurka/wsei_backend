package wsei.backend.lab4.database.entities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import wsei.backend.lab4.models.UserModel;

@Entity
@Table(name = "Users")

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public UserEntity(UserModel model) {
        this.username = model.getUsername();
        this.email = model.getEmail();
        this.firstName = model.getFirstName();
        this.lastName = model.getLastName();

        setPassword(model.getPassword());
    }

    public boolean checkPassword(String Password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            return (this.passwordHash.equals(
                    bytesToHex(md.digest(Password.getBytes()))));
        } catch (NoSuchAlgorithmException e) {
        }
        return false;
    }

    private void setPassword(String Password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            this.passwordHash = bytesToHex(md.digest(Password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public void setPassword(String OldPassword, String NewPassword) {
        try {
            if (checkPassword(OldPassword)) {
                MessageDigest md = MessageDigest.getInstance("SHA3-256");
                this.passwordHash = bytesToHex(md.digest(NewPassword.getBytes()));
            }
        } catch (NoSuchAlgorithmException e) {
        }
    }
}
