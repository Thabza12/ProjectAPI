package Vico.ProjectAPI.repositories;

import Vico.ProjectAPI.domain.ProjectTask;
import Vico.ProjectAPI.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query("{\"id\": {$regex : ?0, $options: 'i'}}")
    Page<User> search(String keyword, Pageable pageable);

    @Query("{\"name\": {$regex : ?0, $options: 'i'}}")
    List<User> search(String keyword);

    User insert(User user);
}
