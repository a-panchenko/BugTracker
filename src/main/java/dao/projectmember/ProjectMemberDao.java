package dao.projectmember;

import model.ProjectMember;

import java.util.List;

public interface ProjectMemberDao {

    List<ProjectMember> getMembers(int projectId);

    void addMember(ProjectMember projectMember);

    void removeMembers(int projectId);
}
