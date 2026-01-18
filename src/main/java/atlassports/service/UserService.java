package atlassports.service;

import atlassports.model.User;
import atlassports.model.dto.UpsertUserDto;
import atlassports.model.dto.UserDto;

public interface UserService {

    UserDto createUser(UpsertUserDto userDto);
    User getLoggedInUser();
}
