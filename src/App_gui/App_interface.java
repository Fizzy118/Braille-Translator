package App_gui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import Main.brailleMain;
/**
 * This is the code providing graphic interface for "brailleMain" aplication
 * @author Zuzanna Adamiuk & Piotr Kielak
 */
public class App_interface extends JFrame
{
    String path;
    
    public App_interface() {

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        GroupLayout layout = new GroupLayout(mainPanel);
        mainPanel.setPreferredSize(new Dimension(750,550));
        mainPanel.setLayout(layout);
        this.getContentPane().add(mainPanel);

        JLabel label=new JLabel("Choose image with text written in Braille:");
        mainPanel.add(label);

        JFileChooser fc=new JFileChooser();
        mainPanel.add(fc);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and JPG images", "png", "jpg");
        fc.addChoosableFileFilter(filter);

       ActionListener actionChoose = new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent ae) {
                File file = fc.getSelectedFile();
                path = file.getAbsolutePath();
                JOptionPane.showMessageDialog(null, "Wybrano obraz!");
            }
       };
       fc.addActionListener(actionChoose);

        JButton buttonTranslate= new JButton("Translate");
        mainPanel.add(buttonTranslate);
        buttonTranslate.setSize(15,15); 
        ActionListener actionTranslate = new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent ae) {}
        };
        fc.addActionListener(actionTranslate);

        JButton buttonInfo= new JButton(new ImageIcon("./icon.png"));
        mainPanel.add(buttonInfo);
        ActionListener actionInfo = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Braille Translator is a console application that allows user to translate Braille alphabate to traditional English alphabet. \n" +
            "  User needs to prepare a sharp image of a text written in Braille and to provide information about a path to the file. \n" +
            "  Then using OpenCV 4.5.0 app edits the image and using special algorithm translates text to finally save it to a new file.","information",JOptionPane.INFORMATION_MESSAGE);
            }
        };

        buttonInfo.addActionListener(actionInfo);

        this.setTitle("Braille Translator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        layout.setAutoCreateGaps( true );
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

       hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                addComponent(label).addComponent(fc));
       hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
                addComponent(buttonInfo).addComponent(buttonTranslate));
       layout.setHorizontalGroup(hGroup);

       GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
       vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(label).addComponent(buttonInfo));
       vGroup.addGroup(layout.createParallelGroup().addComponent(fc));
       vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
               addComponent(buttonTranslate));
       layout.setVerticalGroup(vGroup);
    }
    
    public String getpath(){
            return path;
    }
}
