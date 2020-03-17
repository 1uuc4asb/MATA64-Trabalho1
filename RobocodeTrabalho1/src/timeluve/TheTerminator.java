package timeluve;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

public class TheTerminator extends AdvancedRobot {
	int direction = 1;
	String alvo = null;
	int contadorNaoViuAlvo = 0;
	
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
		if(this.alvo == null || (this.contadorNaoViuAlvo == 3 && !e.getName().equals(this.alvo))) {
			//System.out.println("Meu alvo e: " + e.getName());
			this.alvo = e.getName(); // Define o novo alvo e reseta o contador
			this.contadorNaoViuAlvo = 0;
		}
		
		
		if(!e.getName().equals(this.alvo)) {
			this.contadorNaoViuAlvo++; // Caso nao tenha visto o inimigo marcado, adiciona ao contador e segue
			return;
		}
		
		//System.out.println("Encontrei um robo");
		this.turnGunRight(this.getHeading() - this.getGunHeading() + e.getBearing()); // Locka arma no inimigo
		this.fire(20); // Atira
		this.turnRadarRight(this.getHeading() - this.getRadarHeading() + e.getBearing()); // Locka mira no inimigo
		
		if((e.getName().equals(this.alvo) && e.getDistance() > 200)) {
			//System.out.println("distante");
			this.setTurnRight(e.getBearing() - 20); // Gira o corpo em uma direcao proxima ao inimigo
			this.ahead(100 * this.direction); // Anda
		}
		else if((e.getName().equals(this.alvo))) {
			//System.out.println("perto");
			this.setTurnRight(e.getBearing() - 90); // Gira o corpo a 90 graus do inimigo
			this.ahead(100 * this.direction); // Anda
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		this.direction *= -1; // Se bati numa parede, troca a direcao
	}
	
	@Override
	public void onHitRobot(HitRobotEvent event) {
		this.direction *= -1; // Se bati num robo, troca a direcao
	}
}
