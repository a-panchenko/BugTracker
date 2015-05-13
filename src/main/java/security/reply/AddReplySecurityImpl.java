package security.reply;

import dto.ReplyDto;
import model.*;
import security.Security;
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

public class AddReplySecurityImpl implements Security<Reply, ReplyDto> {

    private ProjectService projectService = new ProjectServiceImpl();
    private IssueService issueService = new IssueServiceImpl();
    private ProjectMemberService projectMemberService = new ProjectMemberServiceImpl();
    private GroupMemberService groupMemberService = new GroupMemberServiceImpl();

    public Reply secure(ReplyDto replyDto) {
        Reply reply = new Reply();
        reply.setIssueId(replyDto.getIssueId());
        setMessage(replyDto, reply);
        setPoster(replyDto, reply);
        reply.setDate(new Date());
        return reply;
    }

    private void setMessage(ReplyDto replyDto, Reply reply) {
        if (replyDto.getMessage() != null && ! replyDto.getMessage().isEmpty()) {
            reply.setMessage(replyDto.getMessage());
        }
        else {
            throw new NotAllowedException();
        }
    }

    private void setPoster(ReplyDto replyDto, Reply reply) {
        Issue issue = issueService.getIssue(reply.getIssueId());
        Project project = projectService.getProject(issue.getProjectId());
        List<ProjectMember> projectMembers = projectMemberService.getMembers(project.getId());
        List<String> allowed = new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            allowed.add(projectMember.getName());
        }
        allowed.add(project.getProjectLead());
        if (allowed.contains(replyDto.getRequestPerformer())
                || groupMemberService.isUserInGroup(replyDto.getRequestPerformer(), "administrators")) {
            reply.setPoster(replyDto.getRequestPerformer());
        }
        else {
            throw new NotAllowedException();
        }
    }
}
