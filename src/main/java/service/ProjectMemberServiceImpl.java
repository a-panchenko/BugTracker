package service;

import dao.projectmember.ProjectMemberDaoImpl;
import model.ProjectMember;

import java.util.List;

public class ProjectMemberServiceImpl implements ProjectMemberService{

    @Override
    public List<ProjectMember> getMembers(int projectId) {
        return new ProjectMemberDaoImpl().getMembers(projectId);
    }

    @Override
    public void addMember(ProjectMember projectMember) {
        new ProjectMemberDaoImpl().addMember(projectMember);
    }

    @Override
    public void removeMembers(int projectId) {
        new ProjectMemberDaoImpl().removeMembers(projectId);
    }
}
