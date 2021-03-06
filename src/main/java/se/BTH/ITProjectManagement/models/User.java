package se.BTH.ITProjectManagement.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Document(collection = "user")
public class User  {
    @Id
    private String id;
    private String name;
    @NotBlank
    @Email
    private String email;
    private String phone;
    private String city;
    private List<Role> roles;
    private boolean active;
   // private CustomerStatus status;

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd-H-m")
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private String activity;
    @NotBlank
    private  String passwordConfirm;
    public boolean changeActive(){
        return active=!active ;
    }
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }



}
