package se.BTH.ITProjectManagement.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Document(collection = "subTask")
public class SubTask {
    @Id
    private String id;
    private String name;
    private TaskStatus status;
    @Min(value = 0)
    @Positive
    private Integer OEstimate; //planned hours
    private List<Integer> actualHours;
    private List<User> users;

    public static List<Integer> intiActualHoursList(int dayes){
        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i <dayes ; i++) {
            temp.add(0);
        }
        return temp;
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
