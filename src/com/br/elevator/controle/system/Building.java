package com.br.elevator.controle.system;

import java.util.Random;

import java.awt.*;
import javax.swing.JFrame;

import com.br.elevator.controle.system.debug.PassengerManager;
import com.br.elevator.controle.system.debug.Passengers;

public class Building extends Canvas {
	private static final long serialVersionUID = 1L;

	public static final int F = 4;
	private Thread elevation;
	private Thread[] floors;

	public static boolean quit = false;
	private static Building gl;

	public static void main(String[] args) {
		gl = new Building();
		// gl.Start();
	}

	public void paint(Graphics g) {

	}

	public Building() {
		this.elevation = new Elevation(0);
		this.floors = new PassengerManager[Building.F];

		JFrame f = new JFrame();
		f.add(this);
		f.setSize(400, 400);
		f.setVisible(true);

		Random rand = new Random();
		int destiny = rand.nextInt(Building.F);

		PassengerManager.passengers = new Passengers[PassengerManager.n];

		// nesse ponto eu distribuo os passageiros existentes em f andares
		for (int i = 0; i < PassengerManager.n; i++) {
			PassengerManager.passengers[i] = new Passengers(destiny);
		}

		// defino cada andar como uma thread de gerenciamento de passagegiros
		for (int i = 0; i < Building.F; i++) {
			floors[i] = new PassengerManager(i);
		}

		this.elevation.start();
	}

	private void Start() {
		float deltaT = 0;

		while (!quit) {
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

	}

	private void Render() {

	}
}
