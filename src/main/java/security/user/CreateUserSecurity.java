package security.user;

import dto.UserDto;
import model.User;

public interface CreateUserSecurity {

    User secureCreateUser(UserDto userDto);
}
