from bs4 import BeautifulSoup as soup
import requests
from flask import Flask, render_template
import re
import math
import dbFunctions as dbf

##### Flask Section #####
app = Flask(__name__)
@app.route('/')
#####  END SECTION  #####


def main(): 
	##### Establish Database Connection #####
	myDb = dbf.connect()
	myCursor = myDb.cursor()
	##########################################

	##### dataBase Initialization, if option is chosesn
	dbf.initPlatformTable(myDb, myCursor)




	##### Beautiful Soup Section ######
	# headers = requests.utils.default_headers()
	# headers.update({
	#     'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:52.0) Gecko/20100101 Firefox/52.0'
	# })

	# # STOCKX - Grab link for 10.5, could automate sizes based on options
	# stockXData = []
	# myURL = 'https://stockx.com/sneakers/size-10-5/most-popular'

	# myReq = requests.get(myURL, headers=headers)
	# myContent = soup(myReq.content,"html5lib")
	
	# tiles = myContent.findAll('a', {'class':'tile browse-tile'})

	# for x in range(len(tiles)):
	# 	stockXData.append(
	# 		(tiles[x]['id'], tiles[x].div.div.img['src'])
	# 	);
		# stockXData.append({'name':tiles[x]["id"],
		# 				   'image':, tiles[x].div.div.img["src"]
		# 				   'price':int(str(tiles[x].findAll(text=re.compile("^[$]\d+$"))[0])[1:])})
	#####################################


	#dbf.insertStockXData(mydb, mycursor, stockXData)

	#return render_template("index.html", stockX=stockXData)

if __name__ == '__main__':
	#app.run(debug=True)
	main()
