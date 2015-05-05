package security.user;

import dto.UserDto;
import model.User;

public class CreateUserSecurityImpl implements CreateUserSecurity {

    public User secureCreateUser(UserDto userDto) {
        User user = new User();
        setName(userDto, user);
        setPassword(userDto, user);
        return user;
    }

    private void setName(UserDto userDto, User user) {
        if (userDto.getUsername() != null) {
            user.setName(userDto.getUsername());
        }
    }

    private void setPassword(UserDto userDto, User user) {
        if (userDto.getPassword() != null) {
            user.setPassword(SHA256.encrypt(userDto.getPassword()));
        }
    }
}
