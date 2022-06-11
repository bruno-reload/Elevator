package com.br.EaseGameEngine;

public class Vector2 {
	public static final int position = 0, dimension = 1;
	public float x;
	public float y;
	public float width;
	public float height;

	public Vector2(Vector2 position) {
		this.x = position.x;
		this.y = position.y;
		this.width = 0;
		this.height = 0;
	}

	public Vector2(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
		this.width = 0;
		this.height = 0;
	}

	public Vector2() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
	}

	public String toString(int value) {
		if (value == dimension) {
			return "(width: " + width + ", height: " + height + ").";
		} else if (value == position) {
			return "(x: " + x + ", y: " + y + ").";
		}
		return null;
	}

	@Override
	public String toString() {
		return "(x: " + x + ", y: " + y + ") (width: " + width + ", height: " + height + ").";

	}
}