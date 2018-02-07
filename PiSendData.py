import socket
import struct
import sys
import imp
import math

sys.path.append('/usr/local/lib/vmxpi/')
vmxpi = imp.load_source('vmxpi_hal_python', '/usr/local/lib/vmxpi/vmxpi_hal_python.py')

SERVER_IP = "10.49.99.2"
SERVER_PORT = 5800
RATE = 100 # packets sent per second

vmx = vmxpi.VMXPi(False, 50)
ahrs = vmxpi.getAHRS()
time = vmxpi.getTime()

out = bytearray(17)
delay = Math.floor((1/RATE) * math.pow(10,6))

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    struct.pack_into(">d", out, 1, ahrs.GetAngle())
    struct.pack_into(">d", out, 9, ahrs.GetRate())
    chksum = sum(out[1:]) % 255
    struct.pack_into(">B", out, 0, chksum)

    sock.sendto(out, (SERVER_IP, SERVER_PORT))

    time.DelayMicroseconds(delay)
