DROP TABLE PROJECT;
DROP SEQUENCE proj_seq;

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

INSERT INTO PROJECT (project_title, start_date)
VALUES ('Project1', '01/01/2015');
