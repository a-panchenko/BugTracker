package security.user;

import security.Security;
import security.exceptions.EditPasswordException;
import dto.UserDto;
import model.User;
import security.exceptions.NotAllowedException;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;

public class EditUserSecurityImpl implements Security<User, UserDto> {

    private UserService userService = new UserServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public User secure(UserDto userDto) {
        User user = userService.getUser(userDto.getName());
        if (userDto.getPassword() != null) {
            editPassword(userDto, user);
        }
        return user;
    }

    private void editPassword(UserDto userDto, User user) {
        if (groupMemberService.isUserInGroup(userDto.getRequestPerformer(), "administrators")) {
            user.setPassword(SHA256.encrypt(userDto.getPassword()));
        }
        else {
            if (userDto.getName().equals(userDto.getRequestPerformer())) {
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
