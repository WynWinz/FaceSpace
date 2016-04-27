---------------
--Authors:
--Ryan Ching
--Chris Corsi
--Edwin Mellett
---------------

---------------
--Date Started:
--4/9/16
---------------

----------------
--Date Finished:
--
----------------

DROP TABLE Profiles CASCADE CONSTRAINTS;
DROP TABLE Friends CASCADE CONSTRAINTS;
DROP TABLE Groups CASCADE CONSTRAINTS;
DROP TABLE Members CASCADE CONSTRAINTS;
DROP TABLE Messages CASCADE CONSTRAINTS;

PURGE RECYCLEBIN;

CREATE TABLE Profiles (
	profile_ID	integer,
	fname		char(20),
	lname 		char(20),
	email 		char(50) UNIQUE,
	DOB 		date,
	lastLogin 	timestamp,
	CONSTRAINT PK_Profile PRIMARY KEY (profile_ID)
);

CREATE TABLE Friends (
	profile_ID		integer,
	friend_ID		integer,
	established 	integer, --0 or 1
	dateEstablished	date,
	CONSTRAINT PK_Friends PRIMARY KEY (profile_ID, friend_ID),
	CONSTRAINT FK_FriendsToProfiles FOREIGN KEY (profile_ID) REFERENCES Profiles(profile_ID)
		ON DELETE CASCADE
);

CREATE TABLE Groups (
	group_ID	integer,
	groupName 	char(30) UNIQUE,
	description	char(120),
	memberLimit	integer,
	numMembers	integer,
	CONSTRAINT PK_Groups PRIMARY KEY (group_ID)
);

CREATE TABLE Members (
	group_ID 	integer,
	profile_ID	integer,
	CONSTRAINT PK_Members PRIMARY KEY (group_ID, profile_ID),
	CONSTRAINT FK_MembersToGroups FOREIGN KEY (group_ID) REFERENCES Groups(group_ID)
		ON DELETE CASCADE,
	CONSTRAINT FK_MembersToProfiles FOREIGN KEY (profile_ID) REFERENCES Profiles(profile_ID)
		ON DELETE CASCADE
);

CREATE TABLE Messages (
	msg_ID			integer,
	sender_ID		integer,
	recipient_ID	integer,
	subject			char(40),
	msgText			varchar2(100),
	timeSent		timestamp,
	CONSTRAINT PK_Messages PRIMARY KEY (msg_ID),
	CONSTRAINT FK_Sender FOREIGN KEY (sender_ID) REFERENCES Profiles(profile_ID)
		ON DELETE CASCADE,
	CONSTRAINT FK_Recipient FOREIGN KEY (recipient_ID) REFERENCES Profiles(profile_ID)
		ON DELETE CASCADE
);

CREATE OR REPLACE TRIGGER CheckGroupSize
	BEFORE INSERT OR UPDATE ON Groups
	REFERENCING NEW as newRow
FOR EACH ROW
BEGIN
	IF :newRow.numMembers > :newRow.memberLimit THEN
		RAISE_APPLICATION_ERROR(-20001, 'Cannot insert- group is full.');
	END IF;
END;
/


CREATE OR REPLACE TRIGGER updateGroupSize
	AFTER DELETE ON Members
	REFERENCING NEW as newRow
	FOR EACH ROW
	UPDATE Groups
		SET numMembers = (select numMembers from Groups WHERE :newRow.group_ID = group_ID)-1
   		WHERE :newRow.group_ID = group_ID
END;
/
