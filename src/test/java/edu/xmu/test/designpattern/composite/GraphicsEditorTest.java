package edu.xmu.test.designpattern.composite;

import org.junit.Before;
import org.junit.Test;

public class GraphicsEditorTest {
	private GraphicsEditor graphicsEditor;
	private Shape complexShape1;

	@Before
	public void setUp() {
		graphicsEditor = new GraphicsEditor();

		complexShape1 = new ComplexShape();

		((ComplexShape) complexShape1).addComponent(new LineShape());
		((ComplexShape) complexShape1).addComponent(new RectangleShape());

		Shape complexShape2 = new ComplexShape();
		((ComplexShape) complexShape2).addComponent(new LineShape());
		((ComplexShape) complexShape2).addComponent(new LineShape());
		((ComplexShape) complexShape2).addComponent(new RectangleShape());

		((ComplexShape) complexShape1).addComponent(complexShape2);
	}

	@Test
	public void displayTest() {
		graphicsEditor.display(complexShape1);
	}
}
