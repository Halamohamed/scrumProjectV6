package se.BTH.ITProjectManagement.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import se.BTH.ITProjectManagement.models.SubTask;
import se.BTH.ITProjectManagement.repositories.SubTaskRepository;

@Component
public class SubTaskValidator implements Validator {

    @Autowired
    private SubTaskRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SubTask.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SubTask subTask= (SubTask) target;
        if(subTask.getOEstimate()<0){
            errors.rejectValue("OEstimate", "value.subtaskAttr.OEstimate");
        }

    }
}
