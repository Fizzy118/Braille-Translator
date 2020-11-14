/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;

/**
 * Braille Translator is a console application that allows user to translate Braille alphabate to traditional English alphabet. 
 * User needs to prepare a sharp image of a text written in Braille and to provide information about a path to the file. 
 * Then using OpenCV 4.5.0 app edits the image and using special algorithm translates text to finally save it to a new file.
 * 
 * @author Zuzanna Adamiuk & Piotr Kielak
 */
public class brailleMain {

    /**
     * 
     * @param args the command line arguments
     */  
    
//image edition method
        static void image_edition(Mat img_Gray, Mat image)
        {
            System.out.println("The edition has started! I'm currently editing the image...");
            //Grayscale
            Imgproc.cvtColor(image, img_Gray, Imgproc.COLOR_BGR2GRAY);

            //Gaussian Filter
            Imgproc.GaussianBlur(img_Gray, img_Gray, new Size(3,3), 0);
            Imgproc.adaptiveThreshold(img_Gray, img_Gray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 5);
            // Median Filter
            Imgproc.medianBlur(img_Gray, img_Gray, 3);
            Imgproc.threshold(img_Gray, img_Gray, 0, 255, Imgproc.THRESH_OTSU); 
            //Gaussian Filter
            Imgproc.GaussianBlur(img_Gray, img_Gray, new Size(3, 3), 0);
            Imgproc.threshold(img_Gray, img_Gray, 0, 255, Imgproc.THRESH_OTSU);
            //imagie edition message
            System.out.println("The editing is complete!");
        }
        
    public static void main(String[] args) {
        
        String sourcePath = "D:/obrazek.jpg";
        String destinationPath = "D:/obrazek-edited.jpg";
        
        // Welcome message
        System.out.println("Welcome to Braille Translator! \nPath to the chosen photo: " + sourcePath);
        
        // Loading the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        // Preparing matrix for changing an image
        Mat imgGray = new Mat();
        Mat image = Imgcodecs.imread(sourcePath, 1);  

        //Function call
        image_edition(imgGray, image);
        //Saving edited image
        Imgcodecs.imwrite(destinationPath,imgGray );
        
        // Successful operation message
        System.out.println("The photo was succesfully edited and saved to a new file! \nPath to the edited photo: " + destinationPath);
       
    }
}
