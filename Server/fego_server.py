import socket
import RPi.GPIO as GPIO
import time
import threading

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
# Four buttons
GPIO.setup(26, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(19, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(13, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(6, GPIO.IN, pull_up_down=GPIO.PUD_UP)
# One LED
GPIO.setup(5, GPIO.OUT)

s = socket.socket()  # Creates an instance of a socket
Port = 8000  # Port to host server on
maxConnections = 1
IP = socket.gethostname()  # IP Address of local machine
s.bind(('', Port))

# Starts Server
s.listen(maxConnections)
print('Server started on Pi')
x = 0
while True:
    print('Listening')
    # Accepts incoming instructions
    (clientsocket, address) = s.accept()
    print("New connection made! Address is : " + str(address))

    GPIO.output(5, GPIO.HIGH)  # LED blinks for 1 second on successful connection
    time.sleep(1)
    GPIO.output(5, GPIO.LOW)


    def buttonPressed(x):
        print('Button ' + str(x) + ' pressed!')
        GPIO.output(5, GPIO.HIGH)
        time.sleep(0.3)
        GPIO.output(5, GPIO.LOW)


    def sendMsg(y):
        if y == 1:
            clientsocket.send(b'1')
        elif y == 2:
            clientsocket.send(b'2')
        elif y == 3:
            clientsocket.send(b'3')
        elif y == 4:
            clientsocket.send(b'4')
        elif y == 5:
            # Check if client disconnected
            clientsocket.send(b'5')


    def disconnect():
        global x
        try:
            msg = clientsocket.recv(1024).decode()
            if msg == 'e':
                clientsocket.close()
                print('Socket closed.Msg e')
        except socket.error:
            clientsocket.close()
            print('Socket closed. Caught Exception')


    thread = threading.Thread(target=disconnect)
    thread.start()
    # Now send instructiuons to client according to button pressed
    print('Press any button to send commands')
    while True:

        if x == -1:
            x = 0
            print('X -1')
            print(str(x))
            break
        # Keep reading inputs of all the switches
        b1_state = GPIO.input(26)  # Play button
        b2_state = GPIO.input(19)  # Pause button
        b3_state = GPIO.input(13)  # Play playlist 1 button
        b4_state = GPIO.input(6)  # Play playlist 2 button
        try:
            sendMsg(5)
        except socket.error:
            print('Client Disconnected')
            break

        try:
            # Check which button is pressed
            if b1_state == False:
                buttonPressed(1)
                sendMsg(1)
            elif b2_state == False:
                buttonPressed(2)
                sendMsg(2)
            elif b3_state == False:
                buttonPressed(3)
                sendMsg(3)
            elif b4_state == False:
                buttonPressed(4)
                sendMsg(4)

        except socket.error:
            print('Socket closed')
            clientsocket.close()
            print('Press Connect')
            break







