import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.NumberFormatter;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;


public class App {
    private static final int width = 360, height= 470;

    private static File file;
    private static JFrame mainFrame;
    private static JPanel mainpanel;
    static int rowvalue, colvalue;
    private static XPanel imgpanel;
    private static ImageIcon toedit;
    public static void start(){
        //window settings
        mainFrame = new JFrame("Divisore Foto");
        mainFrame.setSize(new Dimension(width,height));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setIconImage(null);
        mainFrame.setLocation(480,480);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        //window component
        mainpanel = new JPanel(new BorderLayout());
        mainFrame.add(mainpanel);
        //top
        JPanel toppanel = new JPanel();
        mainpanel.add(toppanel,BorderLayout.NORTH);
        JLabel description = new JLabel("Press the button to load imagine source");
        JButton loadbutton = new JButton("Load");
        toppanel.add(description);
        toppanel.add(loadbutton);
        //center
        imgpanel = new XPanel();

        //bottom
        JLabel rowtext = new JLabel("row");
        JLabel coltext = new JLabel("col");
        NumberFormatter nf = new NumberFormatter(NumberFormat.getInstance());
        nf.setMaximum(20);
        nf.setMinimum(1);
        nf.setAllowsInvalid(false);
        nf.setCommitsOnValidEdit(true);
        JFormattedTextField rowinput = new JFormattedTextField(nf);
        JFormattedTextField colinput = new JFormattedTextField(nf);
        rowinput.setText("1");
        colinput.setText("1");
        rowinput.setColumns(2);
        colinput.setColumns(2);

        JButton confirmbutton = new JButton("Save");
        confirmbutton.setEnabled(false);

        JPanel bottompanel = new JPanel();
        bottompanel.add(rowtext);
        bottompanel.add(rowinput);
        bottompanel.add(coltext);
        bottompanel.add(colinput);
        bottompanel.add(confirmbutton);
        mainpanel.add(bottompanel,BorderLayout.SOUTH);
        bottompanel.revalidate();

        //buttons behaviour
        loadbutton.addActionListener(e -> {
            JFileChooser filechooser = new JFileChooser();
            filechooser.setDragEnabled(true);
            filechooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory() ||
                            f.getName().contains(".jpg") ||
                            f.getName().contains(".jpeg") ||
                            f.getName().contains(".png");
                }
                @Override
                public String getDescription() {
                    return "jpg,jpeg,png";
                }
            });

            int state = filechooser.showOpenDialog(null);
            file = state == JFileChooser.APPROVE_OPTION ? filechooser.getSelectedFile(): null;
            if(file == null)return;

            confirmbutton.setEnabled(true);
            toedit = new ImageIcon(file.getAbsolutePath());

            imgpanel.setImageIcon(new ImageIcon(toedit.getImage().getScaledInstance(360,360,Image.SCALE_FAST)));
            rowinput.addPropertyChangeListener( new Updater(rowinput,colinput,imgpanel));
            colinput.addPropertyChangeListener( new Updater(rowinput,colinput,imgpanel));



            imgpanel.setPreferredSize(new Dimension(360,360));
            mainpanel.add(imgpanel);
            mainpanel.repaint();
            mainpanel.revalidate();
        });

        confirmbutton.addActionListener(e1 -> {
            JFileChooser folderchooser = new JFileChooser();
            folderchooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return null;
                }
            });
            folderchooser.setCurrentDirectory(new File(file.getParent()));
            folderchooser.setSelectedFile(file);
            folderchooser.setBackground(Color.BLACK);
            folderchooser.setApproveButtonText("Salva");
            int state = folderchooser.showOpenDialog(null);
            if (state != JFileChooser.APPROVE_OPTION) return;

            Image i = toedit.getImage();
            BufferedImage bi =   ((ToolkitImage) i).getBufferedImage();

            int outputW = toedit.getImage().getWidth(null)/colvalue,outputH = toedit.getImage().getHeight(null)/rowvalue;
            for (int j = 0; j < rowvalue; j++) {
                for (int k = 0; k < colvalue; k++) {
                    File f = new File(folderchooser.getSelectedFile().getAbsolutePath()+j+k+".jpg");

                    try {
                        ImageIO.write(bi.getSubimage(k * outputW, j * outputH, outputW,outputH),"jpg",f);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }

        });

    loadTheme();
    }
    //render theme
    private static void loadTheme() {
        Color c1 = Color.BLACK;
        Color c2 = Color.WHITE;
        Color c3 = Color.GREEN;
        mainpanel.setBackground(c1);
        for (Component c:mainpanel.getComponents()) {
            c.setBackground(c1);
            c.setForeground(Color.WHITE);
            for (Component comp:((JPanel)c).getComponents()) {
                comp.setBackground(c1);
                comp.setForeground(Color.WHITE);
                if(comp.getClass() == JButton.class){
                    ((JButton)comp).setBorder(new Border() {
                        @Override
                        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                            g.setColor(c3);
                            g.drawLine(x,y+height-2,x+width,y+height-2);

                        }

                        @Override
                        public Insets getBorderInsets(Component c) {
                            return new Insets(10,30,10,30);
                        }

                        @Override
                        public boolean isBorderOpaque() {
                            return true;
                        }
                    });
                }
            }
        }


    }




}
