package digitrecognition; 
import java.awt.Color;
import javax.swing.*;
/**
 *
 * @author gomit
 */
public class Board extends JFrame{

    public Board() {
        setResizable(false);
        initComponents();
    }
                   
    private void initComponents() {
        sudoku = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        drawing = new javax.swing.JPanel();
        drawArea = new drawArea();
        recog = new javax.swing.JButton();
        cleanDraw = new javax.swing.JButton();
        clean = new javax.swing.JButton();
        image = new recognition();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout());

        sudoku.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridLayout(3, 3));

        
        drawArea.setMaximumSize(new java.awt.Dimension(270, 270));
        drawArea.setMinimumSize(new java.awt.Dimension(270, 270));
        drawArea.setPreferredSize(new java.awt.Dimension(270, 270));
        drawArea.setBackground(Color.WHITE);

        javax.swing.GroupLayout jPaneldrawLayout = new javax.swing.GroupLayout(drawArea);
        drawArea.setLayout(jPaneldrawLayout);
        jPaneldrawLayout.setHorizontalGroup(
            jPaneldrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPaneldrawLayout.setVerticalGroup(
            jPaneldrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );

        recog.setText("Reconocer");
        recog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boolean[][] points;
                points = drawArea.getPoints();
                image.setPoints(points);
                image.setImage();
                //image.printBits();
                int num = image.getNumber();
                drawArea.clear();
                image.clear();
            }
        });

        cleanDraw.setText("Limpiar");
        cleanDraw.setText("Clean");
        cleanDraw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawArea.clear();
                
            }
        });

        javax.swing.GroupLayout drawingLayout = new javax.swing.GroupLayout(drawing);
        drawing.setLayout(drawingLayout);
        drawingLayout.setHorizontalGroup(
            drawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(drawingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(drawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(drawArea, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .addGroup(drawingLayout.createSequentialGroup()
                        .addGroup(drawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(recog, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            .addComponent(cleanDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 55, Short.MAX_VALUE)))
                .addContainerGap())
        );
        drawingLayout.setVerticalGroup(
            drawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(drawingLayout.createSequentialGroup()
                .addComponent(drawArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(recog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cleanDraw)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        getContentPane().add(drawing);

        pack();
    }                      
                  
    private javax.swing.JButton clean;
    private javax.swing.JButton cleanDraw;
    private javax.swing.JPanel drawing;
    private javax.swing.JPanel jPanel1;
    private drawArea drawArea;
    private javax.swing.JButton recog;
    private javax.swing.JPanel sudoku;
    private recognition image;
}
