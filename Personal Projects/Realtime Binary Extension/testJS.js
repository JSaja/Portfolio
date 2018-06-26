var app = angular.module("myApp", ['ngclipboard']);

app.controller("myCtrl", function($scope) {
	$scope.numBits = 8;

	$scope.upDwn = function(str) {
		//Convert to number in case of string
		$scope.numBits = Number($scope.numBits);

		if (str == 'up') {
			$scope.numBits += 1;
		}
		if (str == 'dwn' && $scope.numBits > 0) {
			$scope.numBits -= 1;
		}
	};

	$scope.revString = function(str) {
		newString = '';
		for(i=str.length-1; i>=0; i--) {
			newString += str[i];
		}
		return newString;
	};

	$scope.binaryComplement = function(number) {
		complementString = '';
		for(i=0; i<=number.length-1; i++) {
			if (number[i] == 0) {
				complementString += '1';
			}
			else if (number[i] == 1) {
				complementString += '0';
			}
		}
		return complementString;
	};

	$scope.convertBinary = function(number, bits) {
		power = 1;
		total = 0;
		carryBit = 1;
		binaryString = '';
		complementString = '';
		paddedString = '';
		paddedBinaryString = '';
		addOne = '';
		neg = null;
		negTotal = 0;

		if (bits == null) {
			bits = 0;
		}
		else if (bits < 0) {
			$scope.numBits = Math.abs(bits);
		}

		else if (bits >= 10000) {
			return "Number of bits is too large";
		}
		else if (isNaN(bits)) {
			document.getElementById("numBits").style.color = "red";
			return "Invalid number of bits";
		}
		
		if (number != null) {
			//first check negative number, do two's complement later
			if (number.toString().substring(0,1) == "-") {
				number = number.toString().substring(1, number.length);
				number = Number(number);
				neg = true;
			}
			//edge case
			if (number.toString().substring(0,2) == '--') {
				return;
			}

			//Ensure it is a number
			if (isNaN(number)) {
				document.getElementById("input").style.color = "red";
				return "Invalid input";
			}

			//Don't process numbers that will break
			if(number > 10000000000000000000000000000000000000000000000000000000000000000000000) {
				return "Number is too large";
			}

			//calculate the number of digits needed
			while (Math.pow(2, power) <= number) {
				power += 1;
			}

			for (i=power; i > 0; i--) {
				if ((total + Math.pow(2, i-1) <= number)) {
					total += Math.pow(2, i-1);
					binaryString += '1';
				}
				else {
					binaryString += '0';
				}
			}

			//Bit check after the fact			
			if (bits < binaryString.length) {
				document.getElementById("numBits").style.color = "red";
			}
			else {
				document.getElementById("numBits").style.color = "green";
				//append extra 0's here
				difference = bits - binaryString.length;
				while(difference != 0) {
					paddedString += '0';
					difference--;
				}
			}
			paddedBinaryString = paddedString.concat(binaryString);

			//Two's complement if negative. Build inverse string, then add 1.

			//2^pow must be strictly greater than positive number
			if (neg == true) {

				//edge case of 0
				if (number == 0) {
					return 0;
				}

				//Add an extra bit when not on a number that's a power of 2.
				if (paddedBinaryString.substring(0,1) == '1' && paddedBinaryString.substring(1,paddedBinaryString.length).includes("1")) {
					newStr = '0'.concat(paddedBinaryString);
					document.getElementById("numBits").style.color = "red";
					paddedBinaryString = newStr;
				}

				//complement binary string
				complementString = $scope.binaryComplement(paddedBinaryString);

				//add one to this string, starting from back to front
				for(i=complementString.length-1; i>=0; i--) {
					if (complementString[i] == '1') {
						if (carryBit == 1) {					
							addOne += '0';
						}
						else {
							addOne += '1';
						}
					}
					else if (complementString[i] == '0') {
						if (carryBit == 1) {
							addOne += '1';
							carryBit = 0;
						}
						else {
							addOne += '0';
						}
					}
				}
				return($scope.revString(addOne));
			}
		}
		document.getElementById("input").style.color = 'green';
		return(paddedBinaryString);
	};
});