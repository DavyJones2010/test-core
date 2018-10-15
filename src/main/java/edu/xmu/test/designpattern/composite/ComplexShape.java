package edu.xmu.test.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

public class ComplexShape implements Shape {
	private List<Shape> components = new ArrayList<Shape>();

	public void render() {
		System.out.println("Start render [ComplexShape]");
		for (Shape shape : components) {
			shape.render();
		}
		System.out.println("Finished render [ComplexShape]");
	}

	public void addComponent(Shape shape) {
		components.add(shape);
	}

	public void removeComponent(Shape shape) {
		components.remove(shape);
	}
}
