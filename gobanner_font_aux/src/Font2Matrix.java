import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by emi on 09/02/16.
 */
public class Font2Matrix extends JPanel {

    private BufferedImage image;
    private String text;
    private int fontWidth;

    public Font2Matrix() {
        fillText();
        process();
        printMatrix();
    }

    private void fillText() {
        StringBuilder ascii = new StringBuilder();
        for (int i=32; i < 127; i++) {
            ascii.append(Character.toString ((char) i));
        }
        text = ascii.toString();
    }

    private void process() {

        image = new BufferedImage(3000, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font("Monospaced", Font.PLAIN, 31);
        FontMetrics fontMetrics = g2d.getFontMetrics(font);
        fontWidth = fontMetrics.stringWidth("W");

        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());

        g2d.setPaint(Color.black);

        g2d.setFont(font);
        g2d.drawString(text, 0, 26);

        g2d.setColor(Color.red);
        g2d.drawLine(0, 32, fontWidth * text.length(), 32);

        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -32);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);

        g2d.dispose();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }


    private void printMatrix() {
        StringBuilder matrix = new StringBuilder();
        for (int c=0; c < text.length(); c++) {
            matrix.append("{");
            String sep = "";
            for (int x = c*fontWidth; x < (c*fontWidth) + fontWidth; x++) {
                long totalColumn = 0;
                for (int y = 0; y < 32; y++) {
                    int argb = image.getRGB(x, y);
                    /*
                    int r = (argb)&0xFF;
                    int g = (argb>>8)&0xFF;
                    int b = (argb>>16)&0xFF;
                    int a = (argb>>24)&0xFF;
                    System.out.printf("%d %d %d %d", r, g, b, a);
                    */
                    //System.out.printf("x:%d y:%d = %d\n", x, y, argb);
                    if (argb != -1) {
                        totalColumn += Math.pow(2, y);
                    }
                }
                matrix.append(sep);
                matrix.append(String.format("0x%08X", totalColumn));
                sep = ",";
            }
            matrix.append(String.format("}, //%03d=%s\n", c + 32, Character.toString ((char) (c+32))));
        }
        System.out.println(matrix);
    }

    public static void main(String argv[]) {
        JFrame f = new JFrame();
        f.getContentPane().add(new Font2Matrix());
        f.setSize(500, 100);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }


}


