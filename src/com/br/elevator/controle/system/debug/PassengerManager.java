package com.br.elevator.controle.system.debug;

import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.Semaphore;

import com.br.EaseGameEngine.Vector2;
import com.br.elevator.controle.system.Building;

import java.awt.Canvas;

public class PassengerManager {
	public Passengers[] passengers;
	public static final int n = 11;
	private Random r;
	public static Semaphore sem;

	public PassengerManager(Graphics g, Canvas c) {
		sem = new Semaphore(1);
		this.r = new Random();
		passengers = new Passengers[n];
		
		Vector2 size = new Vector2(0, 0, 10, 25);
		float y = 0;
		float x = Building.floorSize;
		for (int i = 0; i < passengers.length; i++) {
			if (this.r.nextInt(2) == 0) {
				float p = (Building.sizePlataform - Building.floorSize) / size.width;
				x = p * (this.r.nextInt((int) size.width) + 1) + Building.floorSize - size.width;
			} else {

				float p = (Building.sizePlataform - Building.floorSize) / size.width;
				x = c.getWidth() - Building.sizePlataform + p * this.r.nextInt((int) size.width) + Building.floorSize
						- size.width;
			}
			int factor = this.r.nextInt(Building.F);
			y = Building.hightWall * factor + Building.floorSize * factor + Building.floorSize + Building.hightWall
					- size.height;
			// y = Building.hightWall * factor + size.height;
			Vector2 position = new Vector2(x, y);
			passengers[i] = new Passengers(size, position, Building.F - factor, g, c);
			passengers[i].start();
		}
	}
}
