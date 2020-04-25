package nehe.netflixzuulapigateway.Services;

import nehe.netflixzuulapigateway.Models.User;
import nehe.netflixzuulapigateway.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //registerUser
    public String registerUser(User user) {
        try {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            return "An error occurred User not saved!";
        }

        return "User saved";
    }

    public boolean checkIfEmailExists(String email)
    {
        return userRepository.findEmail(email) != null;
    }

    //changePassword
//    public Boolean changePassword(String newPassword,int userId)
//    {
//        if(userRepository.changePassword( new BCryptPasswordEncoder().encode(newPassword) , userId) == 1)
//        {
//            return true;
//        }
//        else
//        {
//            return  false;
//        }


//    public User getOneUser(int userId)
//    {
//        return  userRepository.findById(userId)
//                .orElseThrow( ()-> new UserNotFoundException("User with Id: "+ userId +"does not exist"));
//    }

//    public boolean updateUser(User user)
//    {
//
//        int result1 = userRepository.changeFirstName(user.getFirstName(),user.getId());
//        int result2 = userRepository.changeLastName(user.getLastName(),user.getId());
//        int result3 = userRepository.changePhone(user.getPhone(),user.getId());
//        int result4 = userRepository.changeEmail(user.getEmail(),user.getId());
//
//        if (result1 == 1 && result2 == 1 && result3 == 1 && result4 == 1)
//        {
//            return  true;
//        }else
//        {
//            return false;
//        }
//    }


}