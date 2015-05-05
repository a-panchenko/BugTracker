package security.user;

import dto.UserDto;
import model.User;

public interface EditUserSecurity {

    User secureEditUser(UserDto userDto, String username);
}
