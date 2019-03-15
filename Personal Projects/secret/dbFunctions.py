import mysql.connector as msqlc
import time

def connect():
	mydb = msqlc.connect(
		host='localhost',
		user='Zol',
		password='Huff3021!',
		database='sneakers'
	)
	return mydb

def initPlatformTable(myDb, myCursor):
	sql = 'INSERT into platform (platformId, platformName, platformBase) VALUES (%s, %s, %s)'

	vals = [1, 'StockX', 'https://stockx.com/']

	try:
		print("Attempting to insert platform data")
		myCursor.execute(sql, vals)
		myDb.commit()
	except Exception as e:
		print(e)
		myDb.rollback()


def insertStockXData(myDb, myCursor, nameList):
	sql = 'INSERT INTO basicInfo (shoeName, picLocation) VALUES (%s, %s)'
	
	try:
		myCursor.executemany(sql, nameList)
		myDb.commit()
	except:
		myDb.rollback()