package timeluve;

import robocode.AdvancedRobot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class RoboDoTrabalho extends AdvancedRobot {
	int direction = 1;
	
	@Override
	public void run() {
		setAdjustRadarForRobotTurn(true);// Movimento do radar é independente do movimento do robô
		setAdjustGunForRobotTurn(true); // Movimento da arma é independente do movimento do robô
		setAdjustRadarForGunTurn(true); // Movimento do radar é independente do movimento da arma
		this.turnRadarRight(360); // Scan inicial
		this.execute();
		while(true) {
			if(this.getRadarTurnRemaining() == 0) { // Se eu parei de girar o sensor, ou seja, não estrou trackeando ninguem,
				this.turnRadarRight(90); // Escaneia novamente
				this.execute();
			}
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		System.out.println("Encontrei um robo");
		this.turnGunRight(this.getHeading() - this.getGunHeading() + e.getBearing()); // Locka arma no inimigo
		this.fire(50); // Atira
		this.turnRadarRight(this.getHeading() - this.getRadarHeading() + e.getBearing()); // Locka mira no inimigo
		this.setTurnRight(e.getBearing() - 90); // Gira o corpo a 90 graus do inimigo
		this.ahead(100 * this.direction); // Anda
		
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		this.direction *= -1; // Se bati numa parede, troca a direcao
	}
}
