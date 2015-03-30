package service;

import model.ProjectMember;

import java.util.List;

public interface ProjectMemberService {

    List<ProjectMember> getMembers(int projectId);

    List<ProjectMember> getMembers(int projectId, String group);

    void addMember(ProjectMember projectMember);

    void removeMembers(int projectId);
}
