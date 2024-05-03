package CiroVitiello.CapstoneBackend.controllers;

import CiroVitiello.CapstoneBackend.dto.NewSubDTO;
import CiroVitiello.CapstoneBackend.entities.Subscription;
import CiroVitiello.CapstoneBackend.exceptions.BadRequestException;
import CiroVitiello.CapstoneBackend.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/subscriptions")
public class SubController {

    @Autowired
    private SubscriptionService ss;

    @GetMapping
    public Page<Subscription> getAllSubs(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy) {
        return this.ss.getSubs(page, size, sortBy);
    }

    @GetMapping("/{subId}")
    public Subscription findEventsById(@PathVariable UUID subId) {
        return ss.findById(subId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Subscription save(@RequestBody @Validated NewSubDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.ss.save(body);
    }

    @PutMapping("/{subId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Subscription findByIdAndUpdate(@PathVariable UUID subId, @RequestBody @Validated NewSubDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return this.ss.findByIdAndUpdate(subId, body);
    }

    @DeleteMapping("/{subId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID subId) {
        this.ss.findByIdAndDelete(subId);
    }

    @PostMapping("/upload/{subId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Subscription uploadAvatar(@RequestParam("cover") MultipartFile image, @PathVariable UUID subID) throws IOException {
        return this.ss.uploadImage(image, subID);
    }

}
