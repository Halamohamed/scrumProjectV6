package se.BTH.ITProjectManagement.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Document(collection = "timeData")
public class TimeData {

    public List<String> timeDatalist= new ArrayList<>();


    public void saveTimeData(String timeDat) {
        ObjectOutputStream obj = null;
        timeDat = "The history of every login user ";
        timeDatalist.add(timeDat);

        try {
            obj = new ObjectOutputStream(new FileOutputStream("timeData.dat"));

             obj.writeObject(timeDat);
            obj.flush();
            obj.close();
            System.out.println("Users data saved!");
        } catch (IOException e1) {

            e1.printStackTrace();
        }
    }

    public void LoadTimeData() {

        ObjectInputStream obj = null;
        try {
            obj = new ObjectInputStream(new FileInputStream("timeData.dat"));

            timeDatalist =  (ArrayList<String>)obj.readObject();
            obj.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e1) {

            e1.printStackTrace();
        }
        System.out.println(timeDatalist.size() +" user list loaded.");
    }






}
