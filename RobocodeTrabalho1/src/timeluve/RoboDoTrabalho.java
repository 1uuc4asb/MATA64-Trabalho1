package timeluve;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class RoboDoTrabalho extends AdvancedRobot {
	@Override
	public void run() {
		while(true) {
			this.setAhead(50);
			this.setTurnLeft(45);
			this.execute();
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		this.fire(3);
	}
}
