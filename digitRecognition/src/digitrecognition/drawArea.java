package digitrecognition;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author gomit
 */
public class drawArea extends JPanel implements MouseMotionListener, MouseListener{
    private int px, py;
    private boolean point,painting;
    private boolean[][] points;
    
    	public drawArea() {
            point=painting=false;
            px = py = 0;
            points = new boolean[280][280];
            addMouseListener(this);
            addMouseMotionListener(this);
	}
	
	public void clear() {
            points = new boolean[280][280];
            getGraphics().clearRect(0, 0, 280, 280);
            
	}

	public boolean[][] getPoints() {
		return points;
	}

	public void mousePressed(MouseEvent e) {
		point = painting = true;
	}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		Graphics graphics = getGraphics();
		graphics.setColor(Color.BLACK);
		if (painting && point) {
			graphics.drawLine(x, y, x, y);
			point = false;
		} else if (painting) {
			graphics.drawLine(px,py,x,y);
		}
		px = x;
		py = y;
		if (painting) points[x][y] = true;
	}
	
	public void mouseExited(MouseEvent e) {
		painting = false;
	}
	
	public void mouseEntered(MouseEvent e) {
		painting = true;
	}
	
	public void mouseMoved(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}

}