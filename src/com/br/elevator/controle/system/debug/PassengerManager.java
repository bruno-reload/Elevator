package com.br.elevator.controle.system.debug;

import com.br.elevator.controle.system.Building;

public class PassengerManager extends Thread {
	public static Passengers[] passengers;
	public static final int n = 40;
	private int nOffset;
	
	public PassengerManager(int nOffset) {
		this.nOffset = nOffset;
	}
	
	@Override
	public void run() {
		super.run();
		float deltaT = 0;
		while (!Building.quit) {
			long start = System.currentTimeMillis();

			Input();

			Update(deltaT);

			Render();

			long end = System.currentTimeMillis();
			deltaT = (float) (end - start) / 1000.0f;
		}
	}
	
	private void Input() {

	}

	private void Update(float delta) {
		for(int i = 0; i + nOffset < passengers.length; i += nOffset) {
			//nesse ponto estou acessando os passageros em threads especificos
		}
	}

	private void Render() {

	}
}
