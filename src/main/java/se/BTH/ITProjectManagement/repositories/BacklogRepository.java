package se.BTH.ITProjectManagement.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import se.BTH.ITProjectManagement.models.Sprint;

public interface BacklogRepository extends MongoRepository<Sprint,String> {
}
