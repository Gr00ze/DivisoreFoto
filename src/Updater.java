import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class Updater implements PropertyChangeListener {
    JTextField row,col;
    JPanel imgpanel;
    Updater(JTextField row, JTextField col,JPanel imgpanel){
        this.row = row;
        this.col = col;
        this.imgpanel = imgpanel;
    }
    public void update(){
        App.rowvalue = Integer.parseInt(row.getText()) ;
        App.colvalue = Integer.parseInt(col.getText()) ;
        System.out.println("evento");
        imgpanel.repaint();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        update();
    }
}