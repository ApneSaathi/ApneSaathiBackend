ALTER TABLE KEFUSER.volunteer 
ADD role int(11) NOT NULL AFTER pic DEFAULT 1,
ADD FOREIGN KEY (admin_id) REFERENCES KEFUSER.admin(admin_id) 
AFTER role DEFAULT 2,
ADD status varchar(255) AFTER admin_id;

ALTER TABLE KEFUSER.sr_citizen_info
ADD CONSTRAINT UNIQUE(mobileno),
ADD status varchar(255),
ADD reasons varchar(255),
ADD deboardedon TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD lastname varchar(255) NOT NULL;

ALTER TABLE KEFUSER.volunteer_assignment
ADD role int(11) NOT NULL DEFAULT 1,
ADD admin_id bigint(20),
ADD varchar(255) DEFAULT Assigned;

CREATE TABLE 'KEFUSER'.'upload_files_info'
 (
'uploaded_files_info_id' BIGINT(20) NOT NULL PRIMARY KEY AUTO INCREMENT,
uploaded_file_name VARCHAR(255) ,
'error_file_name' VARCHAR(255),
'uploaded_by_id' BIGINT(20),
'upload_date' TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
'success_count' INT(11),
'failure_count' INT(11)
);

CREATE TABLE 'KEFUSER'.'greivance_update_status'(
'greivance_update_status_id' bigint(20) NOT NULL PRIMARY KEY AUTO INCREMENT,
'tracking_id' bigint(20) NOT NULL,
'call_id'bigint(20), 
'idgreivance' bigint(20) NOT NULL,
'type_of_greivance' varchar(100),
'status' varchar(45),
'raisedby' varchar(50),
'description' varchar(255),
'created_date' TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
'priority' varchar(45) DEFAULT Low,
'lastupdatedon' TIMESTAMP,
'reviewed_by' varchar(50),
'underreviewremarks' varchar(255),
'underreivewdate' timestamp(6),
'resolvedby' varchar(50),
'resolved_remarks' varchar(255),
'resolveddate' TIMESTAMP,
'role' int(11),
'admin_id' bigint(20),
'created_by' varchar(45),
FOREIGN KEY(idgreivance) REFERENCES KEFUSER.greivance_tracking(idgreivance),
FOREIGN KEY (tracking_id) REFERENCES KEFUSER.greivance_tracking(tracking_id)
);

ALTER TABLE KEFUSER.greivance_tracking
ADD phoneno_srcitizen varchar(20),
ADD district_srcitizen varchar(100),
ADD raisedby varchar(50),
ADD reviewedby varchar(50),
ADD resolvedby varchar(50),
ADD role int(11),
ADD admin_id bigint(20),
ADD createdby varchar(45);

ALTER TABLE KEFUSER.srcitizen_greivance_log
ADD admin_id bigint(20),
ADD role int(11),
ADD createdby varchar(45);