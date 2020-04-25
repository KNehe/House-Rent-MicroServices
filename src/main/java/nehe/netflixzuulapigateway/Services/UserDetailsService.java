package nehe.netflixzuulapigateway.Services;


import nehe.netflixzuulapigateway.Models.User;
import nehe.netflixzuulapigateway.Repositories.UserRepository;
import nehe.netflixzuulapigateway.security.MyUserPrincpal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository repository){this.userRepository = repository;}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user  = userRepository.findByEmail(email);

        if (user == null)
            throw  new UsernameNotFoundException("User not found with email "+email);

        return new MyUserPrincpal(user);
    }
}
