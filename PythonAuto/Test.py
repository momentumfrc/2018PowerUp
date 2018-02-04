import sys
import imp
import signal

import RotationSource
import EncoderSource

sys.path.append('/usr/local/lib/vmxpi/')
vmxpi = imp.load_source('vmxpi_hai_python', '/usr/local/lib/vmxpi/vmxpi_hai_python.py')

vmx = vmxpi.VMXPi(False, 50)

rotate = RotationSource(vmx)
move = EncoderSource(vmx)

go = True
def endLoop():
    go = False

while go:
    print("Rotation: %f" % rotate.get())
    print("Encoder: %f" % move.get())
