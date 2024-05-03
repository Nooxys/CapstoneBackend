package CiroVitiello.CapstoneBackend.services;

import CiroVitiello.CapstoneBackend.dto.UserLoginDTO;
import CiroVitiello.CapstoneBackend.entities.User;
import CiroVitiello.CapstoneBackend.exceptions.UnauthorizedException;
import CiroVitiello.CapstoneBackend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService us;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jt;

    public String authenticateUsersAndGenerateToken(UserLoginDTO body) {
        User user = this.us.findByEmail(body.email());
        if ((bcrypt.matches(body.password(), user.getPassword()))) {
            return jt.createToken(user);

        } else {
            throw new UnauthorizedException("Invalid credentials! Please log in again!");
        }
    }
}
