package ru.solomka.profile.profile.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.solomka.profile.profile.ProfileEntity;

@RestController
@RequestMapping("/v1/api/profile")
public class ProfileEditorRestController {

    @PostMapping(produces = "applicaiton/json")
    public ResponseEntity<ProfileEntity> saveProfile() {
        return ResponseEntity.ok(ProfileEntity.builder().build());
    }
}
