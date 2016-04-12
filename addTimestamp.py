# Author: Edwin Mellett
# Date:	  4/12/16

import sys
import string

inserts = []
date = False
time = False
dateIndex = -2

def readFile(fileName):
	global inserts
	sqlInput = open(fileName, 'r')

	for line in sqlInput.readlines():
		line = line.strip('\n')
		inserts.append(line)
	sqlInput.close()

def addTimestamp():
	global inserts
	global date, time
	global dateIndex
	temp = []
	for insert in inserts:
		data = insert.split(',')
		if(time):
			timestamp = ' TIMESTAMP '
			timestamp += data[-1]
			data[-1] = timestamp
		if(date):
			date = ' DATE '
			date += data[dateIndex]
			data[dateIndex] = date
		temp.append((',').join(data))
	inserts = temp

def writeFile(fileName):
	global inserts
	sqlOutput = open(fileName, 'w')
	for insert in inserts:
		sqlOutput.write(insert + '\n')
	sqlOutput.close()

def main():
	global date, time
	global dateIndex
	if(len(sys.argv) is not 5):
		print("python addTimestamp.py time date <input.sql> <output.sql>")
		print("date = 0 if no date date = 1 if there is date")
		print("time = 0 if no timestamp time = 1 if there is timestamp")
		sys.exit(-1);
	if(sys.argv[1] == '1'):
		time = True
	if(sys.argv[2] == '1'):
		date = True
		if(sys.argv[1] == '0'):
			dateIndex = -1;
	readFile(sys.argv[3])
	addTimestamp()
	writeFile(sys.argv[4])

if __name__ == '__main__':
	main()