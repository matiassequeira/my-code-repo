--DROP TABLE IF EXISTS TEST;
CREATE TABLE KUOKUO_FILE
(
	ID VARCHAR(32) PRIMARY KEY,
	NAME VARCHAR(500),
	FULL_NAME VARCHAR(500),
	SCAN_TIME TIMESTAMP,
	EXT_NAME VARCHAR(50),
	PATH VARCHAR(500),
	FOLDER_FLAG VARCHAR(50),
	LAST_MODIFIED_TIME TIMESTAMP,
	CREATION_TIME TIMESTAMP,
	TAG VARCHAR(500),
	RECORD_CREATE_TIME TIMESTAMP,
	RECORD_UPDATE_TIME TIMESTAMP,
);