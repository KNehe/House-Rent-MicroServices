package nehe.netflixzuulapigateway.Repositories;

import nehe.netflixzuulapigateway.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    @Query(value = "SELECT email FROM user WHERE email=?1",nativeQuery = true)
    String findEmail(String email);
}
