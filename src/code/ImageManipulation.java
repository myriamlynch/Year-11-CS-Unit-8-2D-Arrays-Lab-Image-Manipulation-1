package code;

import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args)
    {
        APImage image = new APImage("cyberpunk2077.jpg");
        image.draw();

        blackAndWhite("cyberpunk2077.jpg");
        edgeDetection("cyberpunk2077.jpg", 20);
        reflectImage("cyberpunk2077.jpg");
        rotateImage("cyberpunk2077.jpg");
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile)
    {
        APImage image = new APImage(pathOfFile);
        for(int i = 0; i < image.getHeight(); i++)
        {
            for(int j = 0; j < image.getWidth(); j++)
            {
                Pixel p = image.getPixel(j, i);
                int blue = p.getBlue();
                int red = p.getRed();
                int green = p.getGreen();
                int average = (blue + red + green) / 3;
                Pixel newp = new Pixel(average, average, average);
                image.setPixel(j, i, newp);
            }
        }
        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel)
    {
        int blue = pixel.getBlue();
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int average = (blue + red + green) / 3;
        return average;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile)
    {
        APImage image = new APImage(pathOfFile);
        APImage newImage = new APImage(image.getWidth(), image.getHeight());
        for(int i = 0; i < image.getHeight(); i++)
        {
            for(int j = 0; j < image.getWidth(); j++)
            {
                Pixel p = image.getPixel(j, i);
                int average = getAverageColour(p);
                if(average < 128)
                    newImage.setPixel(j, i, new Pixel(0,0,0));
                else
                    newImage.setPixel(j, i, new Pixel(255, 255, 255));
            }
        }
        newImage.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold)
    {
        APImage image = new APImage("cyberpunk2077.jpg");
        for(int i = image.getHeight()-1; i >= 0; i--)
        {
            for(int j = image.getWidth()-1; j >= 0; j--)
            {
                if ((i==0||j==0))
                {
                    image.setPixel(j, i, new Pixel(255, 255, 255));
                    continue;
                }
                Pixel currentP = image.getPixel(j, i);
                Pixel leftP = image.getPixel(j-1, i);
                Pixel bottomP = image.getPixel(j, i - 1);
                int averageCurr = getAverageColour(currentP);
                int averageLeft = getAverageColour(leftP);
                int averageBot = getAverageColour(bottomP);

                if(Math.abs(averageCurr-averageLeft) > threshold || Math.abs(averageCurr-averageBot) > threshold)
                    image.setPixel(j,i,new Pixel(0,0,0));
                else
                    image.setPixel(j,i,new Pixel(255,255,255));
            }
        }
        image.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile)
    {
        APImage image = new APImage(pathToFile);
        for(int i = 0; i < image.getHeight(); i++)
        {
            int opp = image.getWidth() -1;
            for(int j = 0; j < image.getWidth() / 2; j++)
            {
                Pixel current = image.getPixel(j, i);
                Pixel opposite = image.getPixel(opp, i);
                Pixel temp = new Pixel(current.getRed(), current.getGreen(), current.getBlue());
                image.setPixel(j, i, opposite);
                image.setPixel(opp, i, temp);
                opp--;
            }
        }
        image.draw();

    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile)
    {
        APImage image = new APImage(pathToFile);
        APImage newImage = new APImage(image.getHeight(), image.getWidth());

        for(int i = 0; i < image.getWidth(); i++)
        {
            for(int j = 0; j < image.getHeight(); j++)
            {
                int ind = image.getHeight()-j-1;
                Pixel pixel = image.getPixel(i, j);
                newImage.setPixel(ind, i, pixel);
            }
        }
        newImage.draw();
    }
}
