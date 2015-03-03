DROP TABLE REPLY;
DROP TABLE ISSUE;
DROP TABLE PROJECT;
DROP SEQUENCE proj_seq;
DROP SEQUENCE issue_seq;
DROP SEQUENCE reply_seq;

CREATE TABLE PROJECT (
    project_id    NUMBER(10)     NOT NULL,
    project_title VARCHAR2(20)   NOT NULL,
    start_date    DATE           NOT NULL,
    end_date      DATE,
    CONSTRAINT project_pk         PRIMARY KEY (project_id),
    CONSTRAINT project_title_uniq UNIQUE (project_title));
    
CREATE SEQUENCE proj_seq;

CREATE OR REPLACE TRIGGER proj_seq_trg
BEFORE INSERT ON PROJECT
FOR EACH ROW
BEGIN
    SELECT proj_seq.NEXTVAL
    INTO :new.project_id
    FROM dual;
END;
/

INSERT INTO PROJECT (project_title, start_date) VALUES ('Project1', '01/01/2015');
INSERT INTO PROJECT (project_title, start_date) VALUES ('Project2', '02/01/2015');
INSERT INTO PROJECT (project_title, start_date) VALUES ('Project3', '03/02/2015');
INSERT INTO PROJECT (project_title, start_date) VALUES ('Project4', '04/05/2015');

CREATE TABLE ISSUE (
    issue_id      NUMBER(10)    NOT NULL,
    project_id    NUMBER(10)    NOT NULL,
    issue_title   VARCHAR2(50)  NOT NULL,
    description   VARCHAR2(150) NOT NULL,
    priority      VARCHAR2(10)  NOT NULL,
    status        VARCHAR2(10)  NOT NULL,
    creation_date DATE          NOT NULL,
    solving_date  DATE,
    CONSTRAINT issue_pk PRIMARY KEY (issue_id),
    CONSTRAINT issue_fk FOREIGN KEY (project_id) REFERENCES PROJECT(project_id));
    
CREATE SEQUENCE issue_seq;

CREATE OR REPLACE TRIGGER issue_seq_trg
BEFORE INSERT ON ISSUE
FOR EACH ROW
BEGIN
    SELECT issue_seq.NEXTVAL
    INTO :new.issue_id
    FROM dual;
END;
/

INSERT INTO ISSUE (project_id, issue_title, description, priority, status, creation_date)
VALUES (1, 'Issue1', 'Description1', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, description, priority, status, creation_date)
VALUES (1, 'Issue', 'Description1', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, description, priority, status, creation_date)
VALUES (1, 'Issue01', 'Description1', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, description, priority, status, creation_date)
VALUES (2, 'Issue1', 'Description', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, description, priority, status, creation_date)
VALUES (3, 'Issue1', 'Description1', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, description, priority, status, creation_date)
VALUES (3, 'Issue2', 'Description2', 'Priority2', 'Status2', '02/02/2015');

INSERT INTO ISSUE (project_id, issue_title, description, priority, status, creation_date)
VALUES (4, 'Issue3', 'Description3', 'Priority3', 'Status3', '02/01/2015');

CREATE TABLE REPLY (
    reply_id     NUMBER(10)       NOT NULL,
    issue_id     NUMBER(10)       NOT NULL,
    message      VARCHAR2(150)    NOT NULL,
    post_date    DATE             NOT NULL,
    CONSTRAINT reply_pk PRIMARY KEY (reply_id),
    CONSTRAINT reply_fk FOREIGN KEY (issue_id) REFERENCES ISSUE(issue_id));
    
CREATE SEQUENCE reply_seq;

CREATE OR REPLACE TRIGGER reply_seq_trg
BEFORE INSERT ON REPLY
FOR EACH ROW
BEGIN
    SELECT reply_seq.NEXTVAL
    INTO :new.reply_id
    FROM dual;
END;
/

INSERT INTO REPLY (issue_id, message, post_date)
VALUES (3, 'Reply1', '02/01/2015');

INSERT INTO REPLY (issue_id, message, post_date)
VALUES (3, 'Reply2', '02/02/2015');
