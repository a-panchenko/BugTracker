DROP TABLE GROUPMEMBERS;
DROP TABLE GROUPS;
DROP TABLE REPLY;
DROP TABLE ISSUE;
DROP TABLE PROJECTMEMBERS;
DROP TABLE PROJECT;
DROP TABLE USERS;
DROP SEQUENCE proj_seq;
DROP SEQUENCE issue_seq;
DROP SEQUENCE reply_seq;

CREATE TABLE USERS (
  U_NAME          VARCHAR2(50)    NOT NULL,
  U_PASSWORD      VARCHAR2(100)    NOT NULL,
  U_DESCRIPTION   VARCHAR2(200),
  CONSTRAINT PK_USERS PRIMARY KEY (U_NAME));

CREATE TABLE GROUPS (
  G_NAME          VARCHAR2(50)    NOT NULL,
  G_DESCRIPTION   VARCHAR2(200),
  CONSTRAINT PK_GROUPS PRIMARY KEY (G_NAME));

CREATE TABLE GROUPMEMBERS (
  G_NAME    VARCHAR2(50)  NOT NULL,
  G_MEMBER  VARCHAR2(50)  NOT NULL,
  CONSTRAINT PK_GROUPMEMBERS  PRIMARY KEY (G_NAME, G_MEMBER),
  CONSTRAINT FK1_GROUPMEMBERS FOREIGN KEY (G_NAME)    REFERENCES GROUPS (G_NAME)  ON DELETE CASCADE,
  CONSTRAINT FK2_GROUPMEMBERS FOREIGN KEY (G_MEMBER)  REFERENCES USERS (U_NAME)   ON DELETE CASCADE);

CREATE TABLE PROJECT (
    project_id             NUMBER(10)        NOT NULL,
    project_title          VARCHAR2(50)      NOT NULL,
    project_description    VARCHAR2(2000)    NOT NULL,
    start_date             NUMBER(20)        NOT NULL,
    end_date               NUMBER(20),
    project_lead           VARCHAR2(50),
    CONSTRAINT project_pk         PRIMARY KEY (project_id),
    CONSTRAINT project_fk         FOREIGN KEY (project_lead) REFERENCES USERS (u_name) ON DELETE SET NULL,
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

CREATE TABLE PROJECTMEMBERS (
  project_id    NUMBER(10)    NOT NULL,
  name          VARCHAR2(50)  NOT NULL,
  CONSTRAINT pk_projectmembers  PRIMARY KEY (project_id, name),
  CONSTRAINT fk1_projectmembers FOREIGN KEY (project_id)    REFERENCES PROJECT (project_id)  ON DELETE CASCADE,
  CONSTRAINT fk2_projectmembers FOREIGN KEY (name)          REFERENCES USERS (U_NAME)   ON DELETE CASCADE);

CREATE TABLE ISSUE (
    issue_id            NUMBER(10)        NOT NULL,
    project_id          NUMBER(10)        NOT NULL,
    issue_title         VARCHAR2(100)     NOT NULL,
    issue_description   VARCHAR2(2000)    NOT NULL,
    priority            VARCHAR2(10)      NOT NULL CHECK (priority in ('low', 'middle', 'high')),
    status              VARCHAR2(20)      NOT NULL CHECK (status in ('open', 'in progress', 'resolved', 'testing', 'close')),
    creation_date       NUMBER(20)        NOT NULL,
    solving_date        NUMBER(20),
    creator             VARCHAR2(50),
    assigned            VARCHAR2(50),
    CONSTRAINT issue_pk   PRIMARY KEY (issue_id),
    CONSTRAINT issue_fk1  FOREIGN KEY (project_id) REFERENCES PROJECT (project_id) ON DELETE CASCADE,
    CONSTRAINT issue_fk2  FOREIGN KEY (creator)    REFERENCES USERS (U_NAME) ON DELETE SET NULL,
    CONSTRAINT issue_fk3  FOREIGN KEY (assigned)   REFERENCES USERS (U_NAME) ON DELETE SET NULL);
    
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
    message      VARCHAR2(2000)   NOT NULL,
    post_date    NUMBER(20)       NOT NULL,
    poster       VARCHAR2(50)     NOT NULL,
    CONSTRAINT reply_pk       PRIMARY KEY (reply_id),
    CONSTRAINT reply_fk1      FOREIGN KEY (issue_id)  REFERENCES ISSUE (issue_id) ON DELETE CASCADE,
    CONSTRAINT reply_fk2      FOREIGN KEY (poster)    REFERENCES USERS (u_name) ON DELETE CASCADE);
    
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

INSERT INTO USERS (u_name, u_password, u_description)
VALUES ('user1', '0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e', 'description1');

INSERT INTO USERS (u_name, u_password, u_description)
VALUES ('user2', '6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4', 'description2');

INSERT INTO USERS (u_name, u_password, u_description)
VALUES ('user3', '5906ac361a137e2d286465cd6588ebb5ac3f5ae955001100bc41577c3d751764', 'description3');

INSERT INTO USERS (u_name, u_password, u_description)
VALUES ('user4', 'b97873a40f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b', 'description4');

INSERT INTO PROJECT (project_title, project_description, start_date, project_lead)
VALUES ('Project1', 'Description1', '1430024861112', 'user1');

INSERT INTO PROJECT (project_title, project_description, start_date, project_lead)
VALUES ('Project2', 'Description2', '1430024861112', 'user2');

INSERT INTO PROJECT (project_title, project_description, start_date, project_lead)
VALUES ('Project3', 'Description3', '1430024861112', 'user1');

INSERT INTO PROJECT (project_title, project_description, start_date, project_lead)
VALUES ('Project4', 'Description4', '1430024861112', 'user2');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date, creator)
VALUES (1, 'Issue1', 'Description1', 'low', 'open', '1430024861112', 'user1');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date, creator)
VALUES (1, 'Issue2', 'Description2', 'low', 'open', '1430024861112', 'user1');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date, creator)
VALUES (1, 'Issue3', 'Description3', 'low', 'open', '1430024861112', 'user1');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date, creator)
VALUES (2, 'Issue4', 'Description4', 'low', 'open', '1430024861112', 'user2');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date, creator)
VALUES (3, 'Issue5', 'Description5', 'low', 'open', '1430024861112', 'user1');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date, creator)
VALUES (3, 'Issue6', 'Description6', 'low', 'open', '1430024861112', 'user1');

INSERT INTO ISSUE (project_id, issue_title, issue_description, priority, status, creation_date, creator)
VALUES (4, 'Issue7', 'Description7', 'low', 'open', '1430024861112', 'user2');

INSERT INTO REPLY (issue_id, message, post_date, poster)
VALUES (2, 'Reply1', '1430024861112', 'user1');

INSERT INTO REPLY (issue_id, message, post_date, poster)
VALUES (2, 'Reply2', '1430024861112', 'user2');

INSERT INTO GROUPS (g_name, g_description)
VALUES ('administrators', 'group for admins');

INSERT INTO GROUPS (g_name, g_description)
VALUES ('project-managers', 'group for project managers');

INSERT INTO GROUPS (g_name, g_description)
VALUES ('debugers', 'group for debugers');

INSERT INTO GROUPS (g_name, g_description)
VALUES ('testers', 'group for testers');

INSERT INTO GROUPMEMBERS (g_name, g_member)
VALUES ('administrators', 'user1');

INSERT INTO GROUPMEMBERS (g_name, g_member)
VALUES ('project-managers', 'user2');

INSERT INTO GROUPMEMBERS (g_name, g_member)
VALUES ('debugers', 'user3');

INSERT INTO GROUPMEMBERS (g_name, g_member)
VALUES ('testers', 'user4');

commit;