/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;

/**
 *
 * @author Zuzanna
 */
public class Class1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // piekne powitanie
        System.out.println("Siemka!");
        
        // load the OpenCV native library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        
        // create and print on screen a 3x3 identity matrix
        System.out.println("Create a 3x3 identity matrix...");
	Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
	System.out.println("mat = " + mat.dump());
        
        // prepare to convert a RGB image in gray scale
	String location = "D:/obrazek.jpg";
	System.out.print("Convert the image at " + location + " in gray scale... ");
	// get the jpeg image from the internal resource folder
	Mat image = Imgcodecs.imread(location);
	// convert the image in gray scale
	Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
	// write the new image on disk
	Imgcodecs.imwrite("D:/obrazek-edited.jpg", image);
	System.out.println("Done!");
        
//            Mat imgGrayscale = new Mat();
//
//    Mat image = Imgcodecs.imread("D:/obrazek.jpg", 1);  
//
//
//    Imgproc.cvtColor(image, imgGrayscale, Imgproc.COLOR_BGR2GRAY);
//
//    Imgproc.GaussianBlur(imgGrayscale, imgGrayscale, new Size(3, 3), 0);
//    Imgproc.adaptiveThreshold(imgGrayscale, imgGrayscale, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 4);
//
//    Imgproc.medianBlur(imgGrayscale, imgGrayscale, 3);
//    Imgproc.threshold(imgGrayscale, imgGrayscale, 0, 255, Imgproc.THRESH_OTSU);
//
//    Imgproc.GaussianBlur(imgGrayscale, imgGrayscale, new Size(3, 3), 0);
//    Imgproc.threshold(imgGrayscale, imgGrayscale, 0, 255, Imgproc.THRESH_OTSU);
//
//    Imgcodecs.imwrite( "D:/obrazek-edited.jpg", imgGrayscale );
//        
    }
    
}
