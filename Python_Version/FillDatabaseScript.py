import mysql.connector

config = {
	# Must add proper credentials for program to work
	'user': '',
	'password': '',
	'host': 'localhost',
	'database': 'state_capitals_quiz',
	'raise_on_warnings': True
}

connection = mysql.connector.connect(**config)
cursor = connection.cursor()

with open('StateCapitals.txt', 'r') as reader:
	for line in reader.readlines():
		state, capital = line.rstrip('\n').split('-')
		cursor.execute('insert into state_capitals(state, capital) values(%s, %s)', (state, capital))

connection.commit()
cursor.close()
connection.close()