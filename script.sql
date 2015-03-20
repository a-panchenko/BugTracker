DROP TABLE REPLY;
DROP TABLE ISSUE;
DROP TABLE PROJECT;
DROP TABLE GROUPMEMBERS;
DROP TABLE GROUPS;
DROP TABLE USERS;
DROP SEQUENCE proj_seq;
DROP SEQUENCE issue_seq;
DROP SEQUENCE reply_seq;

CREATE TABLE PROJECT (
    project_id             NUMBER(10)     NOT NULL,
    project_title          VARCHAR2(20)   NOT NULL,
    project_description    VARCHAR2(20)   NOT NULL,
    start_date             DATE           NOT NULL,
    end_date               DATE,
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

CREATE TABLE ISSUE (
    issue_id            NUMBER(10)    NOT NULL,
    project_id          NUMBER(10)    NOT NULL,
    issue_title         VARCHAR2(50)  NOT NULL,
    issue_description   VARCHAR2(150) NOT NULL,
    priority            VARCHAR2(10)  NOT NULL,
    status              VARCHAR2(20)  NOT NULL,
    creation_date       DATE          NOT NULL,
    solving_date        DATE,
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

CREATE TABLE USERS (
  U_NAME VARCHAR(200) NOT NULL,
  U_PASSWORD VARCHAR(50) NOT NULL,
  U_DESCRIPTION VARCHAR(1000));

ALTER TABLE USERS
ADD CONSTRAINT PK_USERS
PRIMARY KEY (U_NAME);

CREATE TABLE GROUPS (
  G_NAME VARCHAR(200) NOT NULL,
  G_DESCRIPTION VARCHAR(1000) NULL);

ALTER TABLE GROUPS
ADD CONSTRAINT PK_GROUPS
PRIMARY KEY (G_NAME);

CREATE TABLE GROUPMEMBERS (
  G_NAME VARCHAR(200) NOT NULL,
  G_MEMBER VARCHAR(200) NOT NULL);

ALTER TABLE GROUPMEMBERS
ADD CONSTRAINT PK_GROUPMEMS
PRIMARY KEY (
    G_NAME,
    G_MEMBER
  );

ALTER TABLE GROUPMEMBERS
ADD CONSTRAINT FK1_GROUPMEMBERS
FOREIGN KEY ( G_NAME )
REFERENCES GROUPS (G_NAME)
ON DELETE CASCADE;

insert into users (u_name, u_password, u_description)
values ('user1', 'password1', 'description1');

insert into users (u_name, u_password, u_description)
values ('user2', 'password2', 'description2');

insert into users (u_name, u_password, u_description)
values ('user3', 'password3', 'description3');

insert into users (u_name, u_password, u_description)
values ('user4', 'password4', 'description4');

insert into groups (g_name, g_description)
values ('administrators', 'group for admins');

insert into groups (g_name, g_description)
values ('project-managers', 'group for project managers');

insert into groups (g_name, g_description)
values ('debugers', 'group for debugers');

insert into groups (g_name, g_description)
values ('testers', 'group for testers');

insert into groupmembers (g_name, g_member)
values ('administrators', 'user1');

insert into groupmembers (g_name, g_member)
values ('project-managers', 'user2');

insert into groupmembers (g_name, g_member)
values ('debugers', 'user3');

insert into groupmembers (g_name, g_member)
values ('testers', 'user4');

CREATE OR REPLACE TRIGGER before_delete_issue
BEFORE DELETE ON ISSUE
FOR EACH ROW
BEGIN
    DELETE FROM REPLY WHERE issue_id = :old.issue_id;
END;
/

CREATE OR REPLACE TRIGGER before_delete_project
BEFORE DELETE ON PROJECT
FOR EACH ROW
BEGIN
    DELETE FROM ISSUE WHERE project_id = :old.project_id;
END;
/

INSERT INTO PROJECT (project_title, project_description, start_date)
VALUES ('Project1', 'Description1', '01/01/2015');

INSERT INTO PROJECT (project_title, project_description, start_date)
VALUES ('Project2', 'Description2', '01/02/2015');

INSERT INTO PROJECT (project_title, project_description, start_date)
VALUES ('Project3', 'Description3', '01/01/2015');

INSERT INTO PROJECT (project_title, project_description, start_date)
VALUES ('Project4', 'Description4', '01/02/2015');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date)
VALUES (1, 'Issue1', 'Description1', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date)
VALUES (1, 'Issue2', 'Description2', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date)
VALUES (1, 'Issue3', 'Description3', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date)
VALUES (2, 'Issue4', 'Description4', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date)
VALUES (3, 'Issue5', 'Description5', 'Priority1', 'Status1', '01/01/2015');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date)
VALUES (3, 'Issue6', 'Description6', 'Priority2', 'Status2', '02/02/2015');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date)
VALUES (4, 'Issue7', 'Description7', 'Priority3', 'Status3', '02/01/2015');

INSERT INTO REPLY (issue_id, message, post_date)
VALUES (2, 'Reply1', '02/01/2015');

INSERT INTO REPLY (issue_id, message, post_date)
VALUES (2, 'Reply2', '02/02/2015');

commit;