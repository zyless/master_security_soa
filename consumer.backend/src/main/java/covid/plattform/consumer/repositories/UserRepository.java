package covid.plattform.consumer.repositories;

import covid.plattform.consumer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

// Interface from jpa that enables CRUD operations on the database
@Component
public interface UserRepository extends JpaRepository<User, String> {
}
