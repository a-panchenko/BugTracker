package service;

import model.Issue;
import service.Exception.DaoException;

import java.util.List;

public interface IssueService {
    public void addIssue(Issue issue) throws DaoException;
    public boolean editIssue(int issue_id, Issue issue) throws DaoException;
    public boolean removeIssue(int issue_id) throws DaoException;
    public List<Issue> getAll() throws DaoException;
    public Issue getIssue(int issue_id) throws DaoException;
}
