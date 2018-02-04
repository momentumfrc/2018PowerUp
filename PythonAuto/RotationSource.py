import sys
import imp

sys.path.append('/usr/local/lib/vmxpi/')
vmxpi = imp.load_source('vmxpi_hai_python', '/usr/local/lib/vmxpi/vmxpi_hai_python.py')

class RotationSource:

    def __init__(self, vmx):
        self.vmx = vmx

    def get(self): #return the YAW value of the gyro for use in the PidController
        return vmx.getAHRS().getYaw()

    def reset(self):
        vmx.getAHRS().zeroYaw()
