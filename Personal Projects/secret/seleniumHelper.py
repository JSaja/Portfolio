"""
Objective is to return the data we need in form of lists for every data source
"""
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup as soup
import requests
import json
import urlGenerator as ug
import time

browser = webdriver.Chrome()
urlDict = ug.generateUrls()


def stockXScrape():
	#browser.get(urlDict["2018_18"])
	browser.get('https://stockx.com/sneakers')
	stockXData = []

	while True:
		try:
			element = WebDriverWait(browser, 10).until(lambda x: x.find_element(By.XPATH, "//button[contains(.,'Load More')]"))
			browser.execute_script("window.scrollTo(0, document.body.scrollHeight);")
			element.click()

		except Exception as e:
			print(e)
			break

	print("Starting page scrape")

	page_source = browser.page_source
	link_soup = soup(page_source,'html5lib')
		
	#tiles = link_soup.findAll('a', {'class':'tile browse-tile'})
	tiles = link_soup.findAll('a', class_="tile browse-tile")
	##tiles = link_soup.findAll('a', {'class':'Tile__Card-sc-1tqri8b-0 cxXLSM'})
	#tiles = link_soup.findAll('a')
	print(tiles)
	exit()
	for x in range(len(tiles)-45,len(tiles)):
		print("Num {}/{}".format(x, len(tiles)))
		print(tiles[x])
		exit()
		
		#Goal here is to scrape as much 
		try:
			print("NORMAL SCRAPE: \n{}".format(tiles[x]))
			exit()
			stockXData.append(
				(tiles[x]['id'], str(tiles[x].div.div.img['src']).split("=")[0].strip('"'))
			);

		#If document instead has noscript tag
		except:
			stockXData.append(
				(tiles[x]['id'], str(tiles[x].div.div.noscript).split("=")[3].strip('"'))
			);
			print("StockX LINK: {}".format(tiles[x]['href']))

			
	exit()
	#print(stockXData)
	#need to handle img name errors when there are placeholders
	with open("stockXData.txt", "w+") as f:
		for item in stockXData:
			f.write(json.dumps(item))
			f.write("\n")

	print("Data successfully scraped. Exiting...")
	#browser.close()

def main():
	stockXScrape()

if __name__ == "__main__":
	main()


"""
Tasks for tomorrow --
	Finish scraping mechanism, backup data in text files.
	Handle no picture -- error handling upon display to screen
	Handle no price -- 
	Have seleniumHelper populate database in a smart way -- possibly leave it open to dynamically pull data whenever requested by parameterizing the scrape function and implementing that back in the main file
	find a way to pull "price" when its not listed
	Create database table for products with unknown price / picture
"""