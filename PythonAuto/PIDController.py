import sys
import imp
import threading
import math
sys.path.append('/usr/local/lib/vmxpi/')
vmxpi = imp.load_source('vmxpi_hai_python', '/usr/local/lib/vmxpi/vmxpi_hai_python.py')

class PIDController:
    """PIDController
        Author: Nathan McConnell
    """

    enabled = True
    setpoint = 0
    windup = 10
    # Those vars will come from the PID GUI Tuner when it is finished
    def __init__(self, P, I, D, pidSource, pidOutput, vmxTIME):
        self.Kp = P
        self.Ki = I
        self.Kd = D
        self.PidSource = pidSource
        self.PidOutput = pidOutput
        self.time = vmxTIME

    def run(self):
        lastTime = time.GetCurrentTimeMicroseconds()
        totalerr = 0
        while enabled:
            output = 0
            Err = setpoint - PidSource.get()
            output += Kp * Err
            if abs(Err) < windup:
                totalerr += Err
                output += kI * totalerr
            deltaerr = (err - totalerr) / (time.GetCurrentTimeMicroseconds() - lastTime)
            lastTime = time.GetCurrentTimeMicroseconds()
            output += Kd * deltaerr
            PidOutput.output(output)

    def start(self):
        if not this.enabled or this.thread is None:
            enabled = True
            this.thread = threading.Thread(target=run, name="Pid Thread")
            this.thread.start()
    def stop(self):
        enabled = False
