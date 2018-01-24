package org.usfirst.frc.team4999.robot.commands.pixy;

import java.util.ArrayList;

import org.usfirst.frc.team4999.misc.DetectedVisionObject;
import org.usfirst.frc.team4999.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LogObjects extends Command {
	
	private ArrayList<DetectedVisionObject> objects;
	private long time;

    public LogObjects() {
    	time = System.currentTimeMillis();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.pixy.getObjects().equals(objects)) {
    		objects = Robot.pixy.getObjects();
    		System.out.println("~~~~~ New Frame ~~~~~");
    		System.out.format("Time since last frame: %dms\n",System.currentTimeMillis() - time);
    		time = System.currentTimeMillis();
    		for(DetectedVisionObject obj : objects) {
    			System.out.println(obj);
    		}
    		System.out.println();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
