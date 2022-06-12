package com.br.elevator.controle.system;

import java.util.Random;

import java.awt.*;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.xml.stream.events.Comment;

import com.br.EaseGameEngine.Rectangle;
import com.br.elevator.controle.system.debug.PassengerManager;
import com.br.elevator.controle.system.debug.Passengers;

public class Building extends Canvas {
	private static final long serialVersionUID = 1L;
	private float accumulator = 0;
	public static final int FPS = 60;

	public static final int F = 8;
	private Thread elevation;

	public static boolean quit = false;
	public static int hightWall = 50;
	public static int floorSize = 10;
	private static Building gl;
	private PassengerManager pm;
	public static int sizePlataform;
	private Graphics g;
	private JFrame f;

	public static void main(String[] args) {
		gl = new Building();
		gl.Start();
	}

	public Building() {
		this.f = new JFrame();
		f.add(this);
		f.setSize(400, 600);
		f.setVisible(true);
		setBackground(new Color(111, 254, 210));
		this.g = getGraphics();
		sizePlataform = (getWidth() / 5) * 2;

		hightWall = (getHeight() - (Building.floorSize * Building.F + Building.floorSize)) / Building.F;
		float firstFloor = getHeight() - hightWall - floorSize;
		this.elevation = new Elevation(5,
				new Rectangle((getWidth() / 5) * 2, firstFloor, getWidth() - (getWidth() / 5) * 4, hightWall), g, this);

		pm = new PassengerManager(g, this);

		this.elevation.start();
	}

	private void Start() {
		float deltaT = 0;

		while (!quit) {
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

	private void Input() {

	}

	private void FixeUpdate() {
		super.paint(g);
		Render();
	}

	private void Update(float delta) {
		accumulator += 1f * delta;
	}

	private void Render() {

		g.fillRect(0, 0, 10, getHeight());
		g.fillRect(getWidth() - 10, 0, 10, getHeight());

		for (int i = 0; i <= F; i++) {
			if (i == 0) {
				g.fillRect(0, 0, getWidth(), floorSize);
				continue;
			} else if (i == F) {
				g.fillRect(0, getHeight() - floorSize, getWidth(), floorSize);
			}
			g.fillRect(0, i * (hightWall + floorSize), sizePlataform, floorSize);
			g.fillRect(getWidth() - sizePlataform, i * (hightWall + floorSize), sizePlataform, floorSize);

		}
		for (int i = 0; i < pm.passengers.length; i++) {
			pm.passengers[i].Render();
		}
		((Elevation) elevation).Render();

	}
}
