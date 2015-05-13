package security.user;

import dto.UserDto;
import model.User;
import security.Security;

public class CreateUserSecurityImpl implements Security<User, UserDto> {

    public User secure(UserDto userDto) {
        User user = new User();
        setName(userDto, user);
        setPassword(userDto, user);
        return user;
    }

    private void setName(UserDto userDto, User user) {
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
    }

    private void setPassword(UserDto userDto, User user) {
        if (userDto.getPassword() != null) {
            user.setPassword(SHA256.encrypt(userDto.getPassword()));
        }
    }
}
