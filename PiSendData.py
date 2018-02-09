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

vmx = vmxpi.VMXPi(False, 50)
time = vmx.getTime()

out = bytearray(17)

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    angle = vmx.getAHRS().GetAngle()
    rate = vmx.getAHRS().GetRate()
    struct.pack_into(">d", out, 1, angle)
    struct.pack_into(">d", out, 9, rate)
    chksum = sum(out[1:]) % 255
    struct.pack_into(">B", out, 0, chksum)
    
    sock.sendto(out, (SERVER_IP, SERVER_PORT))

    time.DelayMilliseconds(DELAY)
