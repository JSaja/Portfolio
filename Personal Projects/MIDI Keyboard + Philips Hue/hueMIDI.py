from qhue import Bridge, create_new_username, QhueException
from time import sleep
import pygame
import pygame.midi as midi
import mido
from pygame.locals import *
from threading import Thread

names = mido.get_output_names()
print(mido.open_output(names[0]))
output = mido.open_output(names[0])
output.send(mido.Message('note_on', note=64, velocity=64))
output.send(mido.Message('note_off', note=64, velocity=64))

#Initialize Hue parameters
#######################
bridgeIP = "192.168.1.2"
#credentialPath = "qhue_username.txt"
# credFile = open(credentialPath, "r")
# username = credFile.read()
# credFile.close()
username = "VF1SCLi3HUzQTZHIJVDB7Pfj1EkRkvDdL8UE4Zcr"

hue = Bridge(bridgeIP, username)
lights = hue.lights()
lightList = []
#Preapproved lights to use, hardcoded
#approvedLights = {x for x in range(4,6)}
#######################

#Initialize MIDI parameters
#######################
pygame.init()
pygame.fastevent.init()
midi.init()

event_get = pygame.fastevent.get
event_post = pygame.fastevent.post

inputID = midi.get_default_input_id()
i = midi.Input(inputID)

keylist = {x for x in range(25)}
#######################

#Parameters:
#    Bri: 0 - 254
#    Hue: 0 - 65535 (25 keys)
#    Sat: 0 - 254
velocity = 0
theHue = 0
saturation = 254
#Only use lights currently connected
try:
	print("LIST OF REACHABLE LIGHTS:")
	for lightNum, item in lights.items():
		if (item.get('state').get('reachable') == True):# and (int(lightNum) in approvedLights):
			print("\t" + (item.get('name')) + ", ID = " + lightNum)
			lightList.append(lightNum)
			#initialize bulbs
			hue.lights[lightNum].state(bri=velocity, hue=theHue, sat=0, on=True)
except QhueException:
	print(QhueException)
	print("Light: " + item.get('name') + " is unreachable. Cannot use this")


#User Picks Light Config
vsMode = input("Velocity-sensitive lights? Y or N: ")
while(vsMode != "Y") and (vsMode != "N"):
	print("\tPlease enter only Y OR N")
	vsMode = input("Velocity-sensitive? Y or N: ")
print("Velocity-sensitive Value: " + vsMode)

def setLights(lightNum, velocity, theHue, saturation):
	hue.lights[lightNum].state(bri=velocity, hue=theHue, sat=saturation, on=True)

print("\nReady For MIDI Input!\n")
going = True
while going:
		events = event_get()
		for e in events:
				if (e.type in [QUIT]) or (e.type in [KEYDOWN]):
					going = False

		if i.poll():
				#Format: [[on/off, keyNum, velocity, 0],  timestamp]
				midi_events = i.read(10)				

				keyNumber = (midi_events[0][0][1]) - 47

				if keyNumber in range(26):
					theHue = 2621 * keyNumber
					if (vsMode == "Y"):
						velocity = midi_events[0][0][2]
					else:
						velocity = 127
					if (midi_events[0][0][0] == 144):
						onOff = 'note_on'
					else:
						onOff = 'note_off'					
				
				for light in lightList:
					hue.lights[light].state(bri=velocity*2, hue=theHue, sat=saturation)
				
				output.send(mido.Message(onOff, note=midi_events[0][0][1], velocity=velocity))
					#print("brightness = " + str(velocity) + ", hue = " + str(theHue) + ", saturation = " + str(saturation))

print("Exit Button Clicked...")
print("Resetting lights")

for light in lightList:
	hue.lights[light].state(bri=254, hue=0, sat=0, on=False)

i.close()
midi.quit()
exit()