package digitrecognition;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author gomit
 */
public class recognition {
    boolean[][] points;
    private BufferedImage image;
    private int[] drawAreaSize;
    private boolean[][] bits;
    private int[] intBits;
    MultilayerNeuralNet neuralNet;

    public recognition() {
       points = new boolean[280][280];
       image = new BufferedImage(280, 280, BufferedImage.TYPE_BYTE_BINARY);
       drawAreaSize = new int[] {280, 280, 0, 0};
       bits = new boolean[10][10];
       intBits = new int[100];
       neuralNet = new MultilayerNeuralNet();
    }
    
    public void clear(){
       points = new boolean[280][280];
       image = new BufferedImage(280, 280, BufferedImage.TYPE_BYTE_BINARY);
       drawAreaSize = new int[] {280, 280, 0, 0};
       bits = new boolean[10][10];
       intBits = new int[100];
    }

    public boolean[][] getPoints() {
        return points;
    }

    public void setPoints(boolean[][] points) {
        this.points = points;
    }
    
    /*Convert the drawing in bits*/
    public void setImage() {
        //Prepare the img
	for (int i = 0; i < points.length; i++){
            for (int j = 0; j < points[i].length; j++) {
		int color = (points[i][j])? -1 : -16777216;
		this.image.setRGB(i, j, color);
                if (points[i][j]) {
                    //System.out.println("Points ["+i+"]["+j+"] => " + points[i][j]);
                    if (i < drawAreaSize[0]){
                        drawAreaSize[0] = i;
                    }
                    if (j < drawAreaSize[1]){
                        drawAreaSize[1] = j;
                    } 
                    if (i > drawAreaSize[2]){
                        drawAreaSize[2] = i;
                    } 
                    if (j > drawAreaSize[3]){
                        drawAreaSize[3] = j;
                    } 
		}
            }
        }
        int sqrlen = drawAreaSize[3] - drawAreaSize[2] - drawAreaSize[1] + drawAreaSize[0]; 
	if (sqrlen > 0) {
            drawAreaSize[2] += sqrlen / 2;
            drawAreaSize[0] -= sqrlen / 2;
	} else {
            drawAreaSize[3] -= sqrlen / 2;
            drawAreaSize[1] += sqrlen / 2;
	}
        
        int w = drawAreaSize[2] - drawAreaSize[0];
	int h = drawAreaSize[3] - drawAreaSize[1];
        //System.out.println("W: " + w + " H: "+ h);
	int x, y;
        int dx = w/10, dy = h/10;
	if (dx == 0) 
            dx = 1;
	if (dy == 0) 
            dy = 1;
        y = x = -1;
	for (int i = drawAreaSize[0]; i <= drawAreaSize[2]; i++) {
            if (x != 9 && (i - drawAreaSize[0]) % dx == 0){
                x++;
            } 
            y = -1;
            for (int j = drawAreaSize[1]; j <= drawAreaSize[3]; j++) {
                if (y != 9 && (j - drawAreaSize[1]) % dy == 0){
                    y++;
                }
                if (bits[x][y]) continue;
		bits[x][y] = points[i][j] || bits[x][y];
            }
        }
    }
    
    public int getNumber(){
        //crete a matrix 10*10 with bits
	for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
		intBits[10*j + i] = (bits[i][j])? 1 : 0;
                //System.out.print(intBits[j]);
            }
        }
        int num = neuralNet.getResult(intBits);
        System.out.println("Num: " + num);
        return num;
    }
    
    public void printBits(){
        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if(bits[i][j]==true) {
                    System.out.print(0);
                }else{
                   System.out.print("-"); 
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
