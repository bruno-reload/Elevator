package com.br.elevator.controle.system;

import java.awt.Graphics;
import java.util.concurrent.Semaphore;
import java.awt.Canvas;
import java.awt.Color;
import com.br.EaseGameEngine.Rectangle;
import com.br.EaseGameEngine.Vector2;
import com.br.elevator.controle.system.debug.PassengerManager;
import com.br.elevator.controle.system.debug.Passengers;

public class Elevation extends Thread {
	public enum STATE {
		moving, stopped
	}

	private static int floor = 0;
	public static Rectangle door;
	private Rectangle base;
	private float accumulator = 0;
	public static final int FPS = 60;
	public static Elevation instance;
	private Vector2 position;
	public static float speed = 1f;
	private Graphics g;
	private Canvas c;
	public static boolean ready = false;
	private Semaphore sem;
	public static Semaphore sem1;
	private static STATE currentState = STATE.moving;
	public static Passengers passanger;
	private static boolean fill = false;

	public Elevation(int floor, Rectangle elevationPit, Graphics g, Canvas c) {
		this.c = c;
		this.g = g;
		Elevation.floor = floor;
		this.position = new Vector2(elevationPit.getPosition().x,
				(Building.F - floor) * Building.hightWall + Building.floorSize * (Building.F - floor + 2));
		this.position.y -= Building.floorSize;
		this.sem = new Semaphore(1);
		this.sem1 = new Semaphore(1);
		float doorWidth = (int) elevationPit.getDimension().width - 8;
		Elevation.door = new Door(elevationPit.getPosition().x + 4, position.y, doorWidth,
				elevationPit.getDimension().height);
		Door.Doorwidth = doorWidth;
		this.base = elevationPit;
		instance = this;
	}

	@Override
	public void run() {
		super.run();
		float deltaT = 0;
		while (!Building.quit) {
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

	public void OpenTheDoor() {
		float result = door.getDimension().width - Math.abs(speed * 3);
		door.getDimension().width = result;
		if (result < 0) {
			((Door) door).SetState(Door.stopped);
			this.sem.release();
			if (!fill) {
				passanger.Enter();
			} else {
				passanger.Exit();
			}
		}
	}

	public static void CloseTheDoor() {
		((Door) door).SetState(Door.opened);
		float result = door.getDimension().width + Math.abs(speed * 3);
		door.getDimension().width = result;
		if (result > Door.Doorwidth) {
			((Door) door).SetState(Door.stopped);
			door.getDimension().width = Door.Doorwidth;
			if (fill) {
				Go();
			}else {
				PassengerManager.sem.release();
			}
		}
	}

	public void GoToFloor(Passengers p) {
		try {
			this.sem.acquire();
			passanger = p;
			Go();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void Go() {
		int current = passanger.GetDestiny();
		if (current - floor < 0)
			speed = Math.abs(speed);
		else if (current - floor > 0) {
			speed = -Math.abs(speed);
		}
		currentState = STATE.moving;
		((Door) door).SetState(Door.closed);
		floor = current;
	}

	private void FixeUpdate() {
		switch (currentState) {
		case moving: {
			MoveElavatorToDestiny();
			movePassengerToDestiny();
			break;
		}
		case stopped: {
			if (((Door) door).GetState() == Door.closed) {
				OpenTheDoor();
			}
			if (((Door) door).GetState() == Door.opened) {
				CloseTheDoor();
			}
			break;
		}
		default:
			break;
		}

	}

	private void movePassengerToDestiny() {
		if (fill && ((Door) door).GetState() == Door.closed) {
			passanger.position.y = door.getPosition().y + door.getDimension().height - passanger.size.height;
		}
	}

	private void MoveElavatorToDestiny() {
		float destiny = GetDestiny();
		position.y += speed;
		door.setPosition(door.getPosition().x, position.y);
		if (speed > 0 && position.y > destiny || speed < 0 && position.y < destiny) {
			currentState = STATE.stopped;
		}
	}

	private int GetDestiny() {
		return (Building.F - floor) * Building.hightWall + Building.floorSize * (Building.F - floor + 2)
				- Building.floorSize;
	}

	public void Render() {
		g.setColor(new Color(113, 80, 234));
		Vector2 position = door.getPosition();
		Vector2 dimension = door.getDimension();

		Vector2 p_base = this.base.getPosition();
		Vector2 d_base = this.base.getDimension();

		g.fillRect((int) position.x, (int) position.y, (int) dimension.width, (int) dimension.height);
		g.fillRect((int) p_base.x, (int) (position.y + dimension.height), (int) d_base.width, (int) Building.floorSize);

	}

	public Rectangle getDoor() {
		return door;
	}

	public void SetFill(boolean value) {
		fill = value;
	}

	private void Input() {

	}

	private void Update(float delta) {
		accumulator += 1f * delta;
	}

	public static void SetFloor(int destiny) {
		floor = destiny;
	}

	public void FreeElevation() {
		fill = false;
	}

}

class Door extends Rectangle {
	public static final int opened = 1, closed = 0, stopped = -1;

	public static float Doorwidth;
	private int current = Door.closed;

	public Door(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public int GetState() {
		return current;
	}

	public void SetState(int current) {
		this.current = current;
	}

}
