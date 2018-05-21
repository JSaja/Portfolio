This project combines the technology of a MIDI Keyboard (MPK Mini II in this case), Philips Hue lights API, and a digital audio worksation (DAW),
to simultaneously create a lightshow experience while making music.

How it works:
  - All code is written in Pyhthon
  - A Python wrapper of the philips Hue API is used to control lighting functions (qhue)
  - the pygame library is used to receive MIDI input from the controller
  - A mapping is created between the MIDI input and lighting controls
  - A virtual MIDI driver (LoopBe1) is used to connect the MIDI output from the program, to MIDI input on FL studio
  
Currently supported devices:
  - MPK Mini II
  - Philips Hue Multicolor Wifi lights
 
Notes on the current state of development:
  - Latency is the biggest obstacle to overcome at this point in time.
  - Sensitive objects such as knobs will inherently provide too many requests to the API in a short amount of time, breaking the program.
