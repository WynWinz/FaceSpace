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

--CREATE TABLE Profiles (
	profile_ID	number(10),
	fname		char(20),
	lname 		char(20),
	email 		char(50),
	DOB 		date,
	lastLogin 	timestamp,
	CONSTRAINT PK_Profile PRIMARY KEY (profile_ID)
);

CREATE TABLE Friends (
	profile_ID		number(10),
	friend_ID		number(10),
	established 	integer, --0 or 1
	dateEstablished	date,
	CONSTRAINT PK_Friends PRIMARY KEY (profile_ID, friend_ID),
	CONSTRAINT FK_FriendsToProfiles FOREIGN KEY (profile_ID) REFERENCES Profiles(profile_ID)
);

--CREATE TABLE Groups (
	group_ID	number(10),
	groupName 	char(30) UNIQUE,
	description	char(120),
	memberLimit	integer,
	numMembers	integer,
	CONSTRAINT PK_Groups PRIMARY KEY (group_ID)
);

--CREATE TABLE Members (
	group_ID 	number(10),
	profile_ID	number(10),
	CONSTRAINT PK_Members PRIMARY KEY (group_ID, profile_ID),
	CONSTRAINT FK_MembersToGroups FOREIGN KEY (group_ID) REFERENCES Groups(group_ID),
	CONSTRAINT FK_MembersToProfiles FOREIGN KEY (profile_ID) REFERENCES Profiles(profile_ID)
);

CREATE TABLE Messages (
	msg_ID			number(10),
	sender_ID		number(10),
	recipient_ID	number(10),
	subject			char(40),
	msgText			varchar2(100),
	timeSent		timestamp,
	CONSTRAINT PK_Messages PRIMARY KEY (msg_ID),
	CONSTRAINT FK_Sender FOREIGN KEY (sender_ID) REFERENCES Profiles(profile_ID),
	CONSTRAINT FK_Recipient FOREIGN KEY (recipient_ID) REFERENCES Profiles(profile_ID)
);