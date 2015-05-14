package service.projectmember;

import model.Project;
import model.ProjectMember;

import java.util.List;
import java.util.Set;

public interface ProjectMemberService {

    List<ProjectMember> getMembers(int projectId);

    List<ProjectMember> getMembers(int projectId, String group);

    Void addMember(ProjectMember projectMember);

    Void removeMember(ProjectMember projectMember);

    Set<String> getMembersToAssign(Project project, String username);

}
