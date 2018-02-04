import sys
import imp
from __future__ import division
sys.path.append('/usr/local/lib/vmxpi/')
vmxpi = imp.load_source('vmxpi_hai_python', '/usr/local/lib/vmxpi/vmxpi_hai_python.py')

class EncoderSource:

    ticksPerMeter = 0

    def __init__(self, vmx):
        self.vmx = vmx #get vmx from main file, cant allocate more than one pi
        _, this.EncoderA, _ = allocateEncoder(0,1) #Whatever pins we decide to use
        _, this.EncoderB, _ = allocateEncoder(2,3)


    def allocateEncoder(self, EncA, EncB):
        channelA = vmxpi.VMXChannelInfo(EncA, VMXPi.EncoderAInput) #set channel for the encoders
        channelB = vmxpi.VMXChannelInfo(EncB, VMXPi.EncoderBInput)
        enc_config = vmxpi.EncoderConfig(vmxpi.EncoderConfig.x4) #specify type of encoders
        return vmx.getIO().ActivateDualchannelResource(channelA, channelB, enc_config) #allocating actual resource for encoder

    def get(self):
            return ((vmx.getIO().Encoder_GetCount(EncoderA)[1] + vmx.getIO().Encoder_GetCount(EncoderB)[1]) / 2) * (1/ticksPerMeter) #average encoders value
