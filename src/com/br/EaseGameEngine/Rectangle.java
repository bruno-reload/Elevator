package com.br.EaseGameEngine;

public class Rectangle {
	private Vector2 position = new Vector2();
	private Vector2 dimesion = new Vector2();

	public Rectangle(float x, float y, float width,float height) {
		position.x = x;
		position.y = y;
		dimesion.width = width;
		dimesion.height = height;
	}

	public Rectangle() {

	}

	public Vector2 getDimension() {
		return dimesion;
	}

	public void setDimesion(float x, float y) {
		this.dimesion.width = x;
		this.dimesion.height = y;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}
}
