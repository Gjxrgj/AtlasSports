package atlassports.service;

import atlassports.model.User;
import atlassports.model.dto.PatchUserDto;
import atlassports.model.dto.UpsertUserDto;
import atlassports.model.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    UserDto createUser(UpsertUserDto userDto);
    User getLoggedInUser();
    UserDto getMe();
    ResponseEntity<UserDto> updateMe(PatchUserDto body);
}
