package security.reply;

import dto.ReplyDto;
import model.*;
import security.exceptions.NotAllowedException;
import service.groupmember.GroupMemberService;
import service.groupmember.GroupMemberServiceImpl;
import service.issue.IssueService;
import service.issue.IssueServiceImpl;
import service.project.ProjectService;
import service.project.ProjectServiceImpl;
import service.projectmember.ProjectMemberService;
import service.projectmember.ProjectMemberServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddReplySecurityImpl implements AddReplySecurity {

    private ProjectService projectService = new ProjectServiceImpl();
    private IssueService issueService = new IssueServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public Reply secureAddReply(ReplyDto replyDto, String username) {
        Reply reply = new Reply();
        setIssueId(replyDto, reply);
        setMessage(replyDto, reply);
        setPoster(reply, username);
        reply.setDate(new Date());
        return reply;
    }

    private void setIssueId(ReplyDto replyDto, Reply reply) {
        if (replyDto.getIssueId() != null) {
            reply.setIssueId(Integer.valueOf(replyDto.getIssueId()));
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setMessage(ReplyDto replyDto, Reply reply) {
        if (replyDto.getMessage() != null && ! replyDto.getMessage().isEmpty()) {
            reply.setMessage(replyDto.getMessage());
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setPoster(Reply reply, String username) {
        Issue issue = issueService.getIssue(reply.getIssueId());
        Project project = projectService.getProject(issue.getProjectId());
        List<ProjectMember> projectMembers = projectMemberService.getMembers(project.getId());
        List<String> allowed = new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            allowed.add(projectMember.getName());
        }
        allowed.add(project.getProjectLeed());
        if (allowed.contains(username) || groupMemberService.isUserInGroup(username, "administrators")) {
            reply.setPoster(username);
        }
        else {
            throw new NotAllowedException();
        }
    }
}
