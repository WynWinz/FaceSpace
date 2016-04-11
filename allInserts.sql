
--Profiles
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (1,'Aiko','Gemma','et@malesuadamalesuadaInteger.org',TO_DATE('1984-04-02', 'YYYY-MM-DD'),TO_TIMESTAMP('2015-03-01 13:31:42','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (2,'Cynthia','Neve','tempor@consequatnecmollis.co.uk',TO_DATE('1987-01-15', 'YYYY-MM-DD'),TO_TIMESTAMP('2014-12-27 13:14:59','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (3,'Petra','Tashya','aliquam.arcu@temporest.org',TO_DATE('2011-12-01', 'YYYY-MM-DD'),TO_TIMESTAMP('2014-06-29 15:58:50','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (4,'Jessica','Nicole','vitae.erat@accumsaninterdum.ca',TO_DATE('2009-08-29', 'YYYY-MM-DD'),TO_TIMESTAMP('2015-05-16 20:31:16','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (5,'Brody','Ray','ipsum@nullavulputate.net',TO_DATE('1979-06-15', 'YYYY-MM-DD'),TO_TIMESTAMP('2014-08-17 03:41:39','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (6,'Tanisha','Kathleen','Etiam.ligula@duiFusce.edu',TO_DATE('2014-10-01', 'YYYY-MM-DD'),TO_TIMESTAMP('2014-11-05 03:28:30','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (7,'Cole','Melinda','erat.eget@elitpretium.edu',TO_DATE('1973-10-12', 'YYYY-MM-DD'),TO_TIMESTAMP('2015-02-19 05:05:13','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (8,'Imani','Farrah','conubia.nostra.per@ipsumDonecsollicitudin.org',TO_DATE('1974-06-22', 'YYYY-MM-DD'),TO_TIMESTAMP('2015-09-13 10:34:02','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (9,'Iola','Nash','scelerisque.scelerisque@sem.edu',TO_DATE('1969-08-16', 'YYYY-MM-DD'),TO_TIMESTAMP('2015-11-13 07:22:03','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Profiles (profile_ID,fname,lname,email,DOB,lastLogin) VALUES (10,'Zachary','Teegan','quam.Curabitur.vel@iaculis.ca',TO_DATE('2000-04-01',' YYYY-MM-DD'),TO_TIMESTAMP('2015-09-01 00:47:55','YYYY-MM-DD HH24:MI:SS'));

--Friends
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (1,6,1,TO_DATE('1987-02-12', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (2,10,1,TO_DATE('2015-11-23', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (3,4,0,TO_DATE('2007-03-05', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (4,1,1,TO_DATE('1960-08-30', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (5,6,1,TO_DATE('1993-01-27', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (6,6,0,TO_DATE('1994-08-29', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (7,10,1,TO_DATE('1963-04-30', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (8,4,0,TO_DATE('1965-08-26', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (9,5,0,TO_DATE('1969-06-23', 'YYYY-MM-DD'));
INSERT INTO Friends (profile_ID,friend_ID,established,dateEstablished) VALUES (10,2,1,TO_DATE('2008-12-05', 'YYYY-MM-DD'));

--Groups
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (1,'G1','adipiscing, enim mi tempor lorem,',3,77);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (2,'G2','testing',6,95);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (3,'G3','eu, euismod ac, fermentum vel,',23,51);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (4,'G4','lobortis. Class aptent taciti sociosqu',5,33);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (5,'G5','at pretium aliquet, metus urna',4,99);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (6,'G6','neque sed dictum eleifend, nunc',16,68);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (7,'G7','Cum sociis natoque penatibus et',3,79);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (8,'G8','dolor vitae dolor. Donec fringilla.',15,12);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (9,'G9','eu dui. Cum sociis natoque',26,81);
INSERT INTO Groups (group_ID,groupName,description,memberLimit,numMembers) VALUES (10,'G10','feugiat nec, diam. Duis mi',27,23);

--Members
INSERT INTO Members (group_ID,profile_ID) VALUES (1,1);
INSERT INTO Members (group_ID,profile_ID) VALUES (2,2);
INSERT INTO Members (group_ID,profile_ID) VALUES (3,3);
INSERT INTO Members (group_ID,profile_ID) VALUES (4,4);
INSERT INTO Members (group_ID,profile_ID) VALUES (5,5);
INSERT INTO Members (group_ID,profile_ID) VALUES (6,6);
INSERT INTO Members (group_ID,profile_ID) VALUES (7,7);
INSERT INTO Members (group_ID,profile_ID) VALUES (8,8);
INSERT INTO Members (group_ID,profile_ID) VALUES (9,9);
INSERT INTO Members (group_ID,profile_ID) VALUES (10,10);

--Messages
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (1,9,7,'molestie. Sed','commodo at, libero. Morbi',TO_TIMESTAMP('2015-06-26 11:49:57','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (2,4,6,'Donec nibh','fringilla ornare placerat, orci',TO_TIMESTAMP('2016-08-10 14:07:40','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (3,3,4,'Donec est.','Vivamus nisi. Mauris nulla.',TO_TIMESTAMP('2016-06-16 06:01:07','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (4,6,6,'Maecenas iaculis','Proin sed turpis nec',TO_TIMESTAMP('2016-09-17 06:25:25','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (5,2,4,'lobortis. Class','sed, est. Nunc laoreet',TO_TIMESTAMP('2016-03-29 22:11:30','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (6,2,2,'et, lacinia','mauris blandit mattis. Cras',TO_TIMESTAMP('2016-01-22 11:12:22','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (7,7,7,'Vivamus euismod','justo eu arcu. Morbi',TO_TIMESTAMP('2017-03-07 12:43:36','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (8,1,1,'ornare, libero','Curabitur dictum. Phasellus in',TO_TIMESTAMP('2015-05-17 12:07:14','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (9,8,1,'mauris. Suspendisse','at risus. Nunc ac',TO_TIMESTAMP('2017-02-15 05:21:49','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Messages (msg_ID,sender_ID,recipient_ID,subject,msgText,timeSent) VALUES (10,3,7,'Aliquam rutrum','nec, malesuada ut, sem.',TO_TIMESTAMP('2015-09-10 20:05:23','YYYY-MM-DD HH24:MI:SS'));