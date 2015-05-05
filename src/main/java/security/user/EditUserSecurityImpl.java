package security.user;

import security.exceptions.EditPasswordException;
import dto.UserDto;
import model.User;
import security.exceptions.NotAllowedException;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;

public class EditUserSecurityImpl implements EditUserSecurity {

    private UserService userService = new UserServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public User secureEditUser(UserDto userDto, String username) {
        User user = userService.getUser(userDto.getUsername());
        if (userDto.getPassword() != null) {
            editPassword(userDto, user, username);
        }
        return user;
    }

    private void editPassword(UserDto userDto, User user, String username) {
        if (groupMemberService.isUserInGroup(username, "administrators")) {
            user.setPassword(SHA256.encrypt(userDto.getPassword()));
        }
        else {
            if (userDto.getUsername().equals(username)) {
                if (user.getPassword().equals(SHA256.encrypt(userDto.getOldPassword()))) {
                    user.setPassword(SHA256.encrypt(userDto.getPassword()));
                }
                else {
                    throw new EditPasswordException();
                }
            }
            else {
                throw new NotAllowedException();
            }
        }
    }
}
