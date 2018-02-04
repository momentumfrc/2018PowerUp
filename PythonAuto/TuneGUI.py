from Tkinter import *
import win32clipboard

class Application(Frame):

    def change_inc(self):
        pass

    def clear_vars(self):
        kP = 0
        kI = 0
        kD = 0
        print("Current vars cleared")

    def printCurrentPID(self):

        win32clipboard.OpenClipboard()
        win32clipboard.EmptyClipboard()
        win32clipboard.SetClipboardText("Kp:%f Ki:%f Kd:%f" % (kP,kI,kD))
        win32clipboard.CloseClipboard()

    def createWidgets(self):

        pSlider = Scale(self, from_=0, to=5, orient=HORIZONTAL)
        iSlider = Scale(self, from_=0, to=5, orient=HORIZONTAL)
        dSlider = Scale(self, from_=0, to=5, orient=HORIZONTAL)
        pSlider.grid(row=0 , column=0, columnspan=2, rowspan=1, sticky=N+S+E+W)
        iSlider.grid(row=1 , column=0, columnspan=2, rowspan=1, sticky=N+S+E+W)
        dSlider.grid(row=2 , column=0, columnspan=2, rowspan=1, sticky=N+S+E+W)
        self.set_inc = Button(self)
        self.set_inc["text"] = "Set Increment"
        self.set_inc["command"] = self.printCurrentPID
        self.set_inc.grid(row=3, column=0 , columnspan=2, rowspan=1, sticky=N+S+E+W)

        self.clear = Button(self)
        self.clear["text"] = "Clear Kp, Ki, Kd"
        self.clear["command"] = self.clear_vars
        self.clear.grid(row=4 , column=0 , columnspan=2, rowspan=1, sticky=N+S+E+W)

    def __init__(self, master=None):
        Frame.__init__(self, master)
        self.grid()
        #self.pack()
        self.createWidgets()

root = Tk()
app = Application(master=root)
app.mainloop()
