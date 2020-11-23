/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App_gui;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author piotr
 */
public class App_interface extends JFrame
{
    public App_interface()
    {
 //setting layout and main panel initiation
    JPanel mainPanel = new JPanel();
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    GroupLayout layout = new GroupLayout(mainPanel);
    mainPanel.setPreferredSize(new Dimension(400,250));
    mainPanel.setLayout(layout);
    this.getContentPane().add(mainPanel);
    
    
     
    //label initiation
    JLabel label=new JLabel("Choose imagie with Braille language");
    mainPanel.add(label);

    //tree initiation
    JTree tree=new JTree();
    mainPanel.add(tree);
    tree.setSize(200,200); 
    
    //buttons initiation
    JButton button1= new JButton("Translate");
    mainPanel.add(button1);
    button1.setSize(15,15); 
    //button1.addActionListener();  
    
    JButton button2= new JButton(new ImageIcon("C:\\Users\\piotr\\Desktop\\Braille-Trans\\Braille-Project\\src\\App_gui\\info.png"));
    mainPanel.add(button2);
    button2.setSize(10,10); 
    //button2.addActionListener();  
    
    //Check box initiation
    JCheckBox checkBox=new JCheckBox("save as .txt");  
    mainPanel.add(checkBox);
    
    //setting title and size
    this.setTitle("Braille Translator");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    
    layout.setAutoCreateGaps( true );
    layout.setAutoCreateContainerGaps(true);
    GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

   hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
            addComponent(label).addComponent(tree).addComponent(checkBox));
   hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).
            addComponent(button2).addComponent(button1));
   layout.setHorizontalGroup(hGroup);


   GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
   vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
            addComponent(label).addComponent(button2));
   vGroup.addGroup(layout.createParallelGroup().addComponent(tree));
   vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
            addComponent(checkBox).addComponent(button1));
   layout.setVerticalGroup(vGroup);
}

    public static void main(String[] args)
    {
        Runnable thread=new Runnable()
        {
            @Override
            public void run()
            {
                App_interface app = new App_interface();
                app.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(thread);
    }
}

class information extends JDialog
{
    public information()
    {
        information infoPanel=new information();
        infoPanel.setPreferredSize(new Dimension(400,350));
        this.getContentPane().add(infoPanel);
        JTextArea info_text=new JTextArea("Braille Translator is a console application that allows user to translate Braille alphabate to traditional English alphabet. \n" +
        "  User needs to prepare a sharp image of a text written in Braille and to provide information about a path to the file. \n" +
        "  Then using OpenCV 4.5.0 app edits the image and using special algorithm translates text to finally save it to a new file ");  
        infoPanel.add(info_text);
        
        this.setTitle("Info");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(300,200);
        
    }
    public void runs(){
        information dialog=new information();
        dialog.setVisible(true);
        }
}
