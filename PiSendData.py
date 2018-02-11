import socket
import struct
import sys
import imp
import math

sys.path.append('/usr/local/lib/vmxpi/')
vmxpi = imp.load_source('vmxpi_hal_python', '/usr/local/lib/vmxpi/vmxpi_hal_python.py')

SERVER_IP = "10.49.99.2"
SERVER_PORT = 5800
DELAY = 20 # ms
PACKET_LENGTH = 32

vmx = vmxpi.VMXPi(False, 50)
time = vmx.getTime()

out = bytearray(PACKET_LENGTH)

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    angle = vmx.getAHRS().GetAngle()
    rate = vmx.getAHRS().GetRate()
    xV = vmx.getAHRS().GetVelocityX()
    yV = vmx.getAHRS().GetVelocityY()
    struct.pack_into(">d", out, 0, angle)
    struct.pack_into(">d", out, 8, rate)
    struct.pack_into(">d", out, 16, xV)
    struct.pack_into(">d", out, 24, yV)
    if vmx.getAHRS().IsCalibrating():
        print("Calibrating... angle: %.2f, rate: %.2f, xV: %.2f, yV:%.2f" % (angle, rate, xV, yV))
    elif not vmx.getAHRS().IsConnected():
        print("Not connected...")
    else:
        print("Sending angle %.2f rate %.2f, xV: %.2f, yV:%.2f" % (angle, rate, xV, yV))
        sock.sendto(out, (SERVER_IP, SERVER_PORT))

    time.DelayMilliseconds(DELAY)
