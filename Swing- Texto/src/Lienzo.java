import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

public class Lienzo extends JPanel {

	private String texto = "Texto de prueba";
	private Font font;
	private int fontSize;
	private boolean centrado;
	private boolean ascent;
	private boolean descent;
	private boolean leading;
	
	private Stroke dotted1 = new BasicStroke(
			1, BasicStroke.CAP_BUTT, 
			BasicStroke.JOIN_BEVEL, 0, 
			new float[] {12, 12}, 0);
	
	private Stroke dotted2 = new BasicStroke(
			1, BasicStroke.CAP_BUTT, 
			BasicStroke.JOIN_BEVEL, 0, 
			new float[] {12,12}, 12);
	
	public Lienzo(int ancho, int alto) {
		setPreferredSize(new Dimension(ancho, alto));
		font = getFont().deriveFont((float)(getFont().getSize() * 4));
		fontSize = font.getSize();
	}
	
	public void setCentrado(boolean centrado) {
		this.centrado = centrado;
		repaint();
	}
	
	public void setAscent(boolean ascent) {
		this.ascent = ascent;
		repaint();
	}
	
	public void setDescent(boolean descent) {
		this.descent = descent;
		repaint();
	}
	
	public void setLeading(boolean leading) {
		this.leading = leading;
		repaint();
	}
	
	public void setFontName(String name) {
		font = new Font(name, Font.PLAIN, fontSize);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		int x = (getWidth() - fm.stringWidth(texto)) / 2;
		int y = getHeight() / 2;
		g.setColor(Color.RED);
		g.drawLine(0, y , getWidth() - 1, y);
		if (centrado)
			y += (fm.getAscent() - fm.getDescent()) / 2;
		g.setColor(Color.BLACK);
		g.drawString(texto, x, y);
		drawLines(g, fm, y);
	}
	
	private void drawLines(Graphics g, FontMetrics fm, int y) {
		int x = getWidth() - 1;
		int ya = y - fm.getMaxAscent();
		int yd = y + fm.getMaxDescent();
		int yl = yd + fm.getLeading();
		
		((Graphics2D) g).setStroke(dotted1);
//		g.setColor(Color.GREEN);
//		g.drawLine(0, y, x, y);
				
		if (ascent) {
			g.setColor(Color.BLUE);
			g.drawLine(0, ya, x, ya);
		}
		if (descent) {
			g.setColor(Color.MAGENTA);
			g.drawLine(0, yd, x, yd);
		}
		if (leading) {
			((Graphics2D) g).setStroke(dotted2);
			g.setColor(Color.DARK_GRAY);
			g.drawLine(0, yl, x, yl);
		}
		
	}
	
}
