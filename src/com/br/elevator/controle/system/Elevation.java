package com.br.elevator.controle.system;

public class Elevation extends Thread {
	private int floor;

	public Elevation(int floor) {
		this.floor = floor;
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

	public void OpenTheDoor() {

	}

	public void CloseTheDoor() {

	}

	public void GoToFloor(int number) {

	}

	private void Input() {

	}

	private void Update(float delta) {
		//aqui devo colocar as atualizações de movimento do elevador
	}

	private void Render() {

	}
}
