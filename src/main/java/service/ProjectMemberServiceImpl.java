package service;

import dao.projectmember.ProjectMemberDaoImpl;
import model.GroupMember;
import model.Project;
import model.ProjectMember;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ProjectMemberServiceImpl implements ProjectMemberService{

    @Override
    public List<ProjectMember> getMembers(int projectId) {
        return new ProjectMemberDaoImpl().getMembers(projectId);
    }

    @Override
    public List<ProjectMember> getMembers(int projectId, String group) {
        return new ProjectMemberDaoImpl().getMembers(projectId, group);
    }

    @Override
    public void addMember(ProjectMember projectMember) {
        new ProjectMemberDaoImpl().addMember(projectMember);
    }

    @Override
    public void removeMembers(int projectId) {
        new ProjectMemberDaoImpl().removeMembers(projectId);
    }

    @Override
    public List<String> getMembersToAssign(Project project, String username) {
        List<String> membersToAssign = new ArrayList<>();
        for (ProjectMember projectMember : getMembers(project.getId(), "debugers")) {
            membersToAssign.add(projectMember.getName());
        }
        GroupMember groupMember = new GroupMemberServiceImpl().getMemberByName(username);
        if (project.getProjectLeed().equals(username) || groupMember.getGroup().equals("administrators")) {
            membersToAssign.add(project.getProjectLeed());
        }
        return membersToAssign;
    }

    @Override
    public List<String> getPossibleCreators(Project project, String username) {
        List<String> possibleCreators = new ArrayList<>();
        for (ProjectMember projectMember : getMembers(project.getId(), "testers")) {
            possibleCreators.add(projectMember.getName());
        }
        GroupMember groupMember = new GroupMemberServiceImpl().getMemberByName(username);
        if (project.getProjectLeed().equals(username) || groupMember.getGroup().equals("administrators")) {
            possibleCreators.add(project.getProjectLeed());
        }
        return possibleCreators;
    }
}
