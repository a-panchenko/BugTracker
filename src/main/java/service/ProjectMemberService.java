package service;

import model.ProjectMember;

import java.util.List;

public interface ProjectMemberService {

    List<ProjectMember> getMembers(int projectId);

    void addMember(ProjectMember projectMember);

    void removeMembers(int projectId);
}
