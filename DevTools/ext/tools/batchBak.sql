CREATE TABLE serverinfo (transdate varchar(10) NOT NULL, taskname varchar(100) NOT NULL, runkey varchar(40) NOT NULL, status varchar(15), subpath varchar(200), server varchar(40) NOT NULL, crttime date, CONSTRAINT runkeyIndex UNIQUE (server,runkey, transdate)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE taskexecutiondetail (runkey varchar(40) NOT NULL, divide varchar(40) NOT NULL, divide_value varchar(400), step varchar(40) NOT NULL, step_value varchar(2000), job varchar(40) NOT NULL, job_value varchar(2000), divide_status varchar(40), step_status varchar(40), job_status varchar(40), crttime date, step_finishMsg varchar(3000), CONSTRAINT runkeyIndex UNIQUE (runkey, divide, step, job)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE taskexecutioninfo (systemname varchar(40) NOT NULL, transdate varchar(10) NOT NULL, taskname varchar(50) NOT NULL, runkey varchar(40) NOT NULL, runkey_value varchar(200), status varchar(10), crttime date, CONSTRAINT runkeyIndex UNIQUE (runkey)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
