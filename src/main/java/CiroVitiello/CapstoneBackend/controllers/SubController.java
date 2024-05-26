package CiroVitiello.CapstoneBackend.controllers;

import CiroVitiello.CapstoneBackend.dto.NewSubDTO;
import CiroVitiello.CapstoneBackend.entities.Subscription;
import CiroVitiello.CapstoneBackend.entities.User;
import CiroVitiello.CapstoneBackend.exceptions.BadRequestException;
import CiroVitiello.CapstoneBackend.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Subscription uploadAvatar(@RequestParam("cover") MultipartFile image, @PathVariable UUID subId) throws IOException {
        return this.ss.uploadImage(image, subId);
    }

    @GetMapping("/me")
    public Page<Subscription> getMySubs(@AuthenticationPrincipal User user,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return this.ss.getSubsByUser(user.getId(), pageable);
    }

    @PutMapping("/{id}/users/me")
    public Subscription addMeToSub(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        return this.ss.addUserToSub(id, user.getId());
    }

    @DeleteMapping("{id}/users/me")
    public Subscription removeMeFromSub(@AuthenticationPrincipal User user, @PathVariable UUID id) {
        return this.ss.removeUserFromSub(id, user.getId());
    }

    @PutMapping("{id}/users/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Subscription addUserToSub(@PathVariable UUID id, @PathVariable UUID userId) {
        return this.ss.addUserToSub(id, userId);
    }

    @DeleteMapping("{id}/users/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Subscription removeUserFromSub(@PathVariable UUID id, @PathVariable UUID userId) {
        return this.ss.removeUserFromSub(id, userId);
    }

}
