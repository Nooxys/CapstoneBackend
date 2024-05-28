package CiroVitiello.CapstoneBackend.services;

import CiroVitiello.CapstoneBackend.dto.NewSubDTO;
import CiroVitiello.CapstoneBackend.entities.Subscription;
import CiroVitiello.CapstoneBackend.entities.User;
import CiroVitiello.CapstoneBackend.exceptions.BadRequestException;
import CiroVitiello.CapstoneBackend.exceptions.NotFoundException;
import CiroVitiello.CapstoneBackend.repositories.SubscriptionDAO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionDAO sd;

    @Autowired
    private Cloudinary cloudinaryUploader;

    @Autowired
    private UserService us;

    public Page<Subscription> getSubs(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.sd.findAll(pageable);
    }

    public Subscription save(NewSubDTO body) {
        Subscription newSub = new Subscription(body.title(), body.description(), body.price(), body.daysOfDuration());
        return this.sd.save(newSub);
    }

    public Subscription findById(UUID id) {
        return this.sd.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Subscription findByIdAndUpdate(UUID id, NewSubDTO body) {
        Subscription found = this.findById(id);
        found.setTitle(body.title());
        found.setDescription(body.description());
        found.setPrice(body.price());
        found.setDaysOfDuration(body.daysOfDuration());
        if (!found.getCover().contains("cloudinary")) found.setTemporaryCover();
        this.sd.save(found);
        return found;
    }

    public Subscription uploadImage(MultipartFile image, UUID Id) throws IOException {
        Subscription found = findById(Id);
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setCover(url);
        sd.save(found);
        return found;
    }

    public void findByIdAndDelete(UUID id) {
        Subscription found = this.findById(id);
        this.sd.delete(found);
    }

    public Subscription addUserToSub(UUID subId, UUID userId) {
        Subscription sub = this.findById(subId);
        User user = this.us.findById(userId);

        if (sub.getUsers().contains(user)) {
            throw new BadRequestException("You are already in this subscription!");
        }
        sub.getUsers().add(user);
        return this.sd.save(sub);
    }

    public Subscription removeUserFromSub(UUID subId, UUID userId) {
        Subscription sub = this.findById(subId);
        User user = this.us.findById(userId);
        if (!sub.getUsers().contains(user)) {
            throw new BadRequestException("User not in this subscription!");
        }
        sub.getUsers().remove(user);
        return this.sd.save(sub);
    }

    public Page<Subscription> getSubsByUser(UUID userId, Pageable pageable) {
        User found = this.us.findById(userId);
        return sd.findAllByUsers(found, pageable);
    }

}
