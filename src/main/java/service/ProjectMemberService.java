package service;

import model.Project;
import model.ProjectMember;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProjectMemberService {

    List<ProjectMember> getMembers(int projectId);

    List<ProjectMember> getMembers(int projectId, String group);

    void addMember(ProjectMember projectMember);

    void removeMembers(int projectId);

    List<String> getMembersToAssign(Project project, String username);

    List<String> getPossibleCreators(Project project, String username);
}
