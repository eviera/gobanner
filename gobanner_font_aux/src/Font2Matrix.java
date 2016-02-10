import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Created by emi on 09/02/16.
 */
public class Font2Matrix extends JPanel {

    private BufferedImage image;

    public Font2Matrix() {
        process();
    }

    private void process() {
        image = new BufferedImage(500, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.white);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());

        g2d.setPaint(Color.black);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 41));
        String s = "ABC";
        g2d.drawString(s, 0, 31);

        g2d.setColor(Color.red);
        g2d.drawLine(0, 32, 500, 32);

        //g2d.drawOval(50, 10, 1, 1);

        computeMatrix();

        g2d.dispose();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }


    private void computeMatrix() {
        for (int x=0; x < 100; x++) {
            for (int y = 0; y < 32; y++) {
                int argb = image.getRGB(x, y);
                /*
                int r = (argb)&0xFF;
                int g = (argb>>8)&0xFF;
                int b = (argb>>16)&0xFF;
                int a = (argb>>24)&0xFF;
                System.out.printf("%d %d %d %d", r, g, b, a);
                */
                System.out.printf("x:%d y:%d = %d\n", x, y, argb);

            }
        }

    }

    public static void main(String argv[]) {
        JFrame f = new JFrame();
        f.getContentPane().add(new Font2Matrix());
        f.setSize(500, 100);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }


}


