import javax.swing.*;
import java.awt.*;

public class XPanel extends JPanel {
    ImageIcon toshow;
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(toshow!=null){
            g.drawImage(toshow.getImage(),0,0,null);
        }
        for (int i = 1; i< App.colvalue; i++){
            int xi = 360/App.colvalue * i ;
            g.drawLine(xi,0,xi,360);
        }
        for (int i = 1; i<App.rowvalue; i++){
            int yi = 360/App.rowvalue * i ;
            g.drawLine(0,yi,360,yi);

        }
        System.out.println("repainted");

    }

    public void setImageIcon(ImageIcon imageIcon) {
        toshow = imageIcon;
    }
}
