import java.awt.*;

public class Pipe {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityX;
    boolean passed = false;

    public Pipe(int postX, int postY, int width, int height, Image image) {
        this.posX = postX;
        this.posY = postY;
        this.width = width;
        this.height = height;
        this.image = image;

        this.velocityX = -3;
        this.passed = false;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int postX) {
        this.posX = postX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int postY) {
        this.posY = postY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
