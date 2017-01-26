import javax.swing.*;

/**
 * Created by Rico on 1/25/2017.
 */
public class SupertrumpCardWithPictures extends SupertrumpCard {
    private ImageIcon cardImage;

    SupertrumpCardWithPictures(String nm,ImageIcon img)
    {
        super(nm);
        cardImage = img;
    }

    public ImageIcon getCardImage() {
        return cardImage;
    }
}
