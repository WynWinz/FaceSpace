# Author: Edwin Mellett
# Date:	  4/12/16

import sys
import string

inserts = []
dateAlso = False

def readFile(fileName):
	global inserts
	sqlInput = open(fileName, 'r')

	for line in sqlInput.readlines():
		line = line.strip('\n')
		inserts.append(line)
	sqlInput.close()

def addTimestamp():
	global inserts
	global dateAlso
	temp = []
	for insert in inserts:
		data = insert.split(',')
		timestamp = ' TIMESTAMP '
		timestamp += data[-1]
		data[-1] = timestamp
		if(dateAlso):
				date = ' DATE '
				date += data[-2]
				data[-2] = date
		temp.append((',').join(data))
	inserts = temp

def writeFile(fileName):
	global inserts
	sqlOutput = open(fileName, 'w')
	for insert in inserts:
		sqlOutput.write(insert + '\n')
	sqlOutput.close()

def main():
	global dateAlso
	if(len(sys.argv) is not 4):
		print("python addTimestamp.py dateAlso <input.sql> <output.sql>")
		print("dateAlso = 0 if just timestamp dateAlso = 1 if there is date as well")
		sys.exit(-1);
	if(sys.argv[1] == '1'):
		dateAlso = True
	readFile(sys.argv[2])
	addTimestamp()
	writeFile(sys.argv[3])

if __name__ == '__main__':
	main()