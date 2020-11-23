import mysql.connector
from random import randrange

def playAGame(connection, cursor, stateCapitals):
	score = 0
	statesUsedInAQuestion = set()
	for i in range(10):
		# Get a random state for the current question that has not been used in a question yet
		while True:
			randomIndex = randrange(len(stateCapitals))
			currentQuestionState = stateCapitals[randomIndex][0]
			if currentQuestionState not in statesUsedInAQuestion:
				break
		statesUsedInAQuestion.add(currentQuestionState)
		currentQuestionCapital = stateCapitals[randomIndex][1]
		potentialAnswers = ['', '', '', '']
		correctAnswerIndex = randrange(4)
		potentialAnswers[correctAnswerIndex] = currentQuestionCapital

		# Fill potentialAnswers list with random capitals
		for potentialAnswerIndex in range(4):
			# Ignore the element at correctAnswerIndex of the potentialAnswers list since this element is 
			# equal to the correct answer
			if potentialAnswerIndex == correctAnswerIndex:
				continue
			# Get random capital that is not currently in the potentialAnswers list
			while True:
				randomIndex = randrange(len(stateCapitals))
				randomCapital = stateCapitals[randomIndex][1]
				if randomCapital not in potentialAnswers:
					break
			potentialAnswers[potentialAnswerIndex] = randomCapital
		
		print(f'What is the capital of {currentQuestionState}?')
		for index, answer in enumerate(potentialAnswers):
			print(f'{index}: {answer}')
		if input() == str(correctAnswerIndex):
			print('\nCorrect!\n')
			score += 1
		else:
			print('\nIncorrect\n')

	while True:
		userInput = input(f'Your score is {score}. Would you like to save your score to the database? (y/n) ')
		if userInput == 'y':
			name = input('Enter the name you would like to save this score with: ')
			cursor.execute("insert into scores(name, score) values(%s, %s)", (name, score))
			connection.commit()
			break
		if userInput == 'n':
			break
		print('Invalid input')

def main():
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

	cursor.execute('select * from state_capitals')
	'''stateCapitals consists of tuples. The first element of each one is a state and the second element is it's capital'''
	stateCapitals = cursor.fetchall()

	print()
	while True:
		userInput = input(
			'Welcome to the state capitals quiz game\nWould you like to\n1: Study\n2: Play a game' + 
			'\n3: View high scores\n4: Exit\n'
		)
		print()
		if userInput == '1':
			for state, capital in stateCapitals:
				print(f'State: {state}, Capital: {capital}')
		elif userInput == '2':
			playAGame(connection, cursor, stateCapitals)
		elif userInput == '3':
			cursor.execute('select * from scores order by score desc')
			for name, score in cursor:
				print(f'Name: {name}, Score: {score}')
		elif userInput == '4':
			break
		else:
			print('Invalid input')
		print()

	cursor.close()
	connection.close()

main()
