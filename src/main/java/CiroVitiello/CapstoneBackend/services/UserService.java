package CiroVitiello.CapstoneBackend.services;

import CiroVitiello.CapstoneBackend.dto.NewUserDTO;
import CiroVitiello.CapstoneBackend.entities.User;
import CiroVitiello.CapstoneBackend.exceptions.BadRequestException;
import CiroVitiello.CapstoneBackend.exceptions.NotFoundException;
import CiroVitiello.CapstoneBackend.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO ud;

    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> getUsers(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.ud.findAll(pageable);
    }

    public User save(NewUserDTO body) {
        this.ud.findByEmail(body.email())
                .ifPresent(user -> {
                    throw new BadRequestException(" email " + user.getEmail() + " already in use!");
                });

        this.ud.findByUsername(body.username())
                .ifPresent(user -> {
                    throw new BadRequestException(" username " + user.getUsername() + " already  in use!");
                });
        User newUser = new User(body.name(), body.surname(), body.username(), body.email(), bcrypt.encode(body.password()), body.birthDate());
        return this.ud.save(newUser);
    }

    public User findById(UUID id) {
        return this.ud.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(UUID id, NewUserDTO body) {
        User found = this.findById(id);
        this.ud.findByEmail(body.email())
                .ifPresent(user -> {
                    throw new BadRequestException(" email " + user.getEmail() + " already in use!");
                });

        this.ud.findByUsername(body.username())
                .ifPresent(user -> {
                    throw new BadRequestException(" username " + user.getUsername() + " already  in use!");
                });

        found.setUsername(body.username());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setPassword(bcrypt.encode(body.password()));
        found.setBirthDate(body.birthDate());
        this.ud.save(found);
        return found;
    }

    public void findByIdAndDelete(UUID id) {
        User found = this.findById(id);
        this.ud.delete(found);
    }

    public User findByEmail(String email) {
        return ud.findByEmail(email).orElseThrow(() -> new NotFoundException("User with " + email + " not found!"));
    }
}