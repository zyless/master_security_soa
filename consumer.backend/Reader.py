#!/usr/bin/env python

import RPi.GPIO as GPIO
from mfrc522 import SimpleMFRC522

reader = SimpleMFRC522()

try:
        id, text = reader.read()
        print(id)
        print(text)
        file = open("user_id.txt", "w")
        file.write(str(id))
        file.close()
finally:
        GPIO.cleanup()

