--FIX Terminal Output

SET LINESIZE 32767;
SET TRIMSPOOL ON;
SET TRIMOUT ON;
SET WRAP OFF;
COLUMN lastLogin FORMAT a15;
COLUMN groupName FORMAT a15;
COLUMN subject FORMAT a15;
COLUMN msgText FORMAT a60;
COLUMN timeSent FORMAT a28;
