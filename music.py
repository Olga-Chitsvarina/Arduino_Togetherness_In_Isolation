import serial
import pygame

# Listen to Arduino at this port.
arduinoData = serial.Serial('/dev/ttyACM0')

# Initialize mixer and load a song.
pygame.mixer.init()
birds = pygame.mixer.Sound("birds.ogg")

# This helps to keep track of wether the song is playing
started1 = False

# Read messages from Arduino.
# Turn on/off songs depending on the state
while True:
    # Print message to console (useful for debugging)
    cc=str(arduinoData.readline())
    print(cc)

    # If the message is StartBirds music:
    # Update state to started
    # Start music
    if("StartBirds" in cc and not started1):
        birds.play()
        started1= True

    # If message is StopBirds music,
    # Stop music,
    # Update state to not started
    if("StopBirds" in cc and started1):
        started1= False
        birds.stop()

# REFERENCES:
# 1) Code from first trial with Python Script for Music in Assignment 2:
# https://github.com/Olga-Chitsvarina/Arduino-Musical-Instrument/commit/9d6f0b9ff605cd5f3fa7680977aacec84ca2638b
# 2) Pygame library:
# https://github.com/pygame/pygame/blob/bd131148444cd6e946aec986c93f9afc44728f85/test/sndarray_test.py
# 3) Python Serial Library:
# https://pyserial.readthedocs.io/en/latest/shortintro.html
# 4) Sound effects were downloaded from this website:
# https://bigsoundbank.com/detail-0595-wind.html
# 5) Music and some parts of code were downloaded and based on code for previous Assignment 3:
# https://github.com/Olga-Chitsvarina/Arduino-Weather-Machine/blob/master/weather/music.py
