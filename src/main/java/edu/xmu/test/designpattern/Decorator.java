package edu.xmu.test.designpattern;

public class Decorator {
	static interface Window {
		public void renderWindow();
	}

	static class SimpleWindow implements Window {
		public void renderWindow() {
			System.out.println(String.format("Start render for: [%s]", this));
			System.out.println(String.format("Finished render for: [%s]", this));
		}
	}

	static abstract class DecoratedWindow implements Window {
		private Window windowReference;

		public DecoratedWindow(Window windowReference) {
			super();
			this.windowReference = windowReference;
		}

		public void renderWindow() {
			windowReference.renderWindow();
		}
	}

	static class ScrollableWindow extends DecoratedWindow {

		private String scrollBarObjectRepresentation;

		public ScrollableWindow(Window windowReference, String scrollBarObjectRepresentation) {
			super(windowReference);
			this.scrollBarObjectRepresentation = scrollBarObjectRepresentation;
		}

		@Override
		public void renderWindow() {
			renderScrollBar();
			super.renderWindow();
		}

		private void renderScrollBar() {
			System.out.println(String.format("Start renderScrollBar for: [%s]", this));
			System.out.println(scrollBarObjectRepresentation);
			System.out.println(String.format("Finished renderScrollBar for: [%s]", this));
		}
	}

	static class FramableWindow extends DecoratedWindow {

		public FramableWindow(Window windowReference) {
			super(windowReference);
		}

		@Override
		public void renderWindow() {
			addFrame();
			super.renderWindow();
		}

		private void addFrame() {
			System.out.println(String.format("Start addFrame for: [%s]", this));
			System.out.println(String.format("Finished addFrame for: [%s]", this));
		}
	}

	public static void main(String[] args) {
		Window w = new FramableWindow(new SimpleWindow());
		w.renderWindow();// simple window with framable capability

		w = new FramableWindow(new ScrollableWindow(new SimpleWindow(), "Scroll"));
		w.renderWindow();// simple window with scroll and framable capability
	}
}
