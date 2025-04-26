package Controller;

/**
 * Exception class for handling image loading errors in JavaFX applications.
 * This class extends RuntimeException and provides additional information about the image path.
 */
public class JavaFXImageException extends RuntimeException {
    private String imagePath;

    public JavaFXImageException(String message,String imagePath) {
        super(message);
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }


}



