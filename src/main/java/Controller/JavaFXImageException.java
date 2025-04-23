package Controller;

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



