package com.br.elevator.controle.system.debug;

import java.awt.Color;

import java.awt.Graphics;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.plaf.DimensionUIResource;

import java.awt.Canvas;
import com.br.EaseGameEngine.Vector2;
import com.br.elevator.controle.system.Building;
import com.br.elevator.controle.system.Elevation;

public class Passengers extends Thread {
	public static final int moving = 1;
	public static final int stopped = 0;
	public int currentState = stopped;
	private int destiny;
	private float accumulator = 0;
	public static final int FPS = 60;
	public Vector2 position;
	public boolean ready = false;
	private Random r;
	public Vector2 size;
	private Graphics g;
	private Canvas c;
	private float speed = 10;
	private float initialPosiont;
	private int side;
	public boolean boarding = true;
	private boolean active = true;

	public Passengers(Vector2 size, Vector2 position, int destiny, Graphics g, Canvas c) {
		this.size = size;
		this.r = new Random();
		this.position = position;
		this.initialPosiont = position.x;
		this.destiny = destiny;
		this.g = g;
		this.c = c;
	}

	@Override
	public void run() {
		super.run();
		float deltaT = 0;
		// requisita elevador
		try {
			PassengerManager.sem.acquire();
			Elevation.instance.GoToFloor(this);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (active) {
			long start = System.currentTimeMillis();

			accumulator += deltaT;
			Input();

			Update(deltaT);

			while (accumulator > 1f / FPS) {
				FixeUpdate();
				accumulator -= (1f / FPS);
			}
			long end = System.currentTimeMillis();
			deltaT = (float) (end - start) / 1000.0f;
		}
	}

	private void FixeUpdate() {
		switch (currentState) {
		case Passengers.moving: {
			if (boarding) {
				// move para fora do elevador
				Elevation.instance.SetFill(true);
				position.x += speed * (side * -1);
				// move para posição inicial
				if (side > 0 && this.position.x < this.initialPosiont
						|| side < 0 && this.position.x > this.initialPosiont) {
					Elevation.instance.FreeElevation();
					currentState = stopped;
					this.interrupt();
					active = false;
				}
			} else {
				// move para dentro do elevador
				if (position.x > c.getWidth() / 2) {
					position.x -= speed;
					side = -1;
				} else {
					position.x += speed;
					side = 1;
				}
			}
			// vai para a posição do elevador
			if (position.x < c.getWidth() / 2 + 5 && position.x > c.getWidth() / 2 - 5) {
				currentState = stopped;
				Elevation.instance.SetFill(true);
				SetDestiny();
				Elevation.CloseTheDoor();
			}
			break;
		}
		case Passengers.stopped: {

			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + currentState);
		}
	}

	private void Update(float deltaT) {

	}

	private void Input() {

	}

	public void Render() {
		g.setColor(Color.red);
		g.fillRect((int) position.x, (int) position.y, (int) size.width, (int) size.height);
	}

	public int GetDestiny() {
		return destiny;
	}

	public void SetDestiny() {
		this.destiny = this.r.nextInt(Building.F) + 1;
	}

	public void Enter() {
		currentState = moving;
		this.boarding = false;
	}

	public void Exit() {
		currentState = moving;
		this.boarding = true;
	}
}
