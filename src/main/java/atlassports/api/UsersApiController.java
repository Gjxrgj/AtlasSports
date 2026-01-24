package atlassports.api;

import atlassports.model.dto.PatchUserDto;
import atlassports.model.dto.UserDto;
import atlassports.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@Controller
@RequestMapping("${openapi.atlasSports.base-path:/api/users}")
public class UsersApiController implements UsersApi {

    private final NativeWebRequest request;
    private final UserService userService;

    @Autowired
    public UsersApiController(NativeWebRequest request, UserService userService) {
        this.request = request;
        this.userService = userService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<UserDto> usersMeGet() {
        return ResponseEntity.ok(userService.getMe());
    }

    @Override
    @PatchMapping("/me")
    public ResponseEntity<UserDto> usersMePatch(@Valid PatchUserDto body) {
        return userService.updateMe(body);
    }
}
