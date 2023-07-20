package main;

public class App {
	private DocFrame df;
	private Controller c;
	private DataHandler dh;
	public App() {
		df = new DocFrame();
		dh = new DataHandler();
		c = new Controller(df, dh);
		df.setListeners(c);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		App a = new App();
		a.df.setVisible(true);
	}

}
