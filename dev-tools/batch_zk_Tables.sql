CREATE TABLE
    serverinfo
    (
        transdate VARCHAR(10) NOT NULL,taskname VARCHAR(100) NOT NULL,runkey VARCHAR(40) NOT NULL,status VARCHAR(15),
        subpath VARCHAR(200),server VARCHAR(40) NOT NULL,crttime DATE,CONSTRAINT runkeyIndex UNIQUE(server,runkey,
        transdate)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci;
CREATE TABLE
   taskexecutiondetail
    (
        runkey VARCHAR(40) NOT NULL,divide VARCHAR(40) NOT NULL,divide_value VARCHAR(400),step VARCHAR(40) NOT NULL,
        step_value VARCHAR(2000),job VARCHAR(40) NOT NULL,job_value VARCHAR(2000),divide_status VARCHAR(40),step_status
        VARCHAR(40),job_status VARCHAR(40),crttime DATE,step_finishMsg VARCHAR(3000),CONSTRAINT runkeyIndex UNIQUE
        (runkey,divide,step,job)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci;
CREATE TABLE
    taskexecutioninfo
    (
        systemname VARCHAR(40) NOT NULL,transdate VARCHAR(10) NOT NULL,taskname VARCHAR(50) NOT NULL,runkey VARCHAR(40)
        NOT NULL,runkey_value VARCHAR(512),status VARCHAR(10),crttime DATE,CONSTRAINT runkeyIndex UNIQUE(runkey)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci;
