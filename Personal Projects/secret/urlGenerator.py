"""Generate StockX URL's"""
import json

def generateUrls():
	urlDict = {}
	for halfSize in range(35, 155, 10):
		halfSize = halfSize/10
		
		if halfSize < 10:
			strInsert = str(halfSize)[0]
			for year in range(2001,2019):
				if year == 2001:
					urlDict["{}_{}".format(year,halfSize)] = 'https://stockx.com/sneakers/size-{}-5?years=before-{}'.format(strInsert, year)
				else:
					urlDict["{}_{}".format(year,halfSize)] = 'https://stockx.com/sneakers/size-{}-5?years={}'.format(strInsert, year)
		else:
			strInsert = str(halfSize)[:2]
			for year in range(2001,2019):
				if year == 2001:
					urlDict["{}_{}".format(year,halfSize)] = 'https://stockx.com/sneakers/size-{}-5?years=before-{}'.format(strInsert, year)
				else:
					urlDict["{}_{}".format(year,halfSize)] = 'https://stockx.com/sneakers/size-{}-5?years={}'.format(strInsert, year)

	for wholeSize in range(40, 190, 10):
		wholeSize = int(wholeSize/10)
		
		if wholeSize < 10:
			strInsert = str(wholeSize)[0]
			for year in range(2001,2019):
				if year == 2001:
					urlDict["{}_{}".format(year,wholeSize)] = 'https://stockx.com/sneakers/size-{}?years=before-{}'.format(strInsert, year)
				else:
					urlDict["{}_{}".format(year,wholeSize)] = 'https://stockx.com/sneakers/size-{}?years={}'.format(strInsert, year)
		else:
			strInsert = str(wholeSize)[:2]
			for year in range(2001,2019):
				if year == 2001:
					urlDict["{}_{}".format(year,wholeSize)] = 'https://stockx.com/sneakers/size-{}?years=before-{}'.format(strInsert, year)
				else:
					urlDict["{}_{}".format(year,wholeSize)] = 'https://stockx.com/sneakers/size-{}?years={}'.format(strInsert, year)
	return urlDict			

def main():
	urlDict = generateUrls()

	for key in urlDict:
		print("KEY: {}".format(key))
		print("VALUE: {}".format(urlDict[key]))

if __name__ == "__main__":
	main()