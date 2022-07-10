package wsei.backend.lab3.database.entities;

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
import wsei.backend.lab3.models.UserModel;

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
    private String Username;
    @NonNull
    @Column(unique = true)
    private String Email;
    @NonNull
    private String FirstName;
    @NonNull
    private String LastName;
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String PasswordHash;

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public UserEntity(UserModel model) {
        this.Username = model.getUsername();
        this.Email = model.getEmail();
        this.FirstName = model.getFirstName();
        this.LastName = model.getLastName();

        setPassword(model.getPassword());
    }

    public boolean checkPassword(String Password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            return (this.PasswordHash.equals(
                    bytesToHex(md.digest(Password.getBytes()))));
        } catch (NoSuchAlgorithmException e) {
        }
        return false;
    }

    private void setPassword(String Password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            this.PasswordHash = bytesToHex(md.digest(Password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public void setPassword(String OldPassword, String NewPassword) {
        try {
            if (checkPassword(OldPassword)) {
                MessageDigest md = MessageDigest.getInstance("SHA3-256");
                this.PasswordHash = bytesToHex(md.digest(NewPassword.getBytes()));
            }
        } catch (NoSuchAlgorithmException e) {
        }
    }
}
