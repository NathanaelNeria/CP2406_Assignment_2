import javax.swing.*;

/**
 * Created by Rico on 1/25/2017.
 */
public class NormalCardWithPictures extends NormalCard {
    private ImageIcon cardImage;
    NormalCardWithPictures(String nm, float hard, float speGra, String cleav, String cryAbu, String ecoV, ImageIcon img)
    {
        super(nm,hard,speGra,cleav,cryAbu,ecoV);
        cardImage = img;
    }

    public ImageIcon getCardImage() {
        return cardImage;
    }
}
