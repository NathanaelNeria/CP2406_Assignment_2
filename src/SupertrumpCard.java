/**
 * Created by Rico on 1/4/2017.
 */
public class SupertrumpCard extends Card {          //Class extend from the Card class

    SupertrumpCard(String nm)
    {
        super(nm);
    }           //Constructing from super

    public String useEffect()                       //Use effect method to return string to be used as command
    {
        String effect = "";
        String x = getName();
        if(x.equals("The Mineralogist"))
        {effect = "C";}
        else if(x.equals("The Geologist"))
        {effect = "NEW";}
        else if(x.equals("The Geophysicist"))
        {effect = "SS";}
        else if(x.equals("The Petrologist"))
        {effect = "A";}
        else if(x.equals("The Miner"))
        {effect = "E";}
        else if(x.equals("The Gemmologist"))
        {effect = "H";}
        return effect;                                     //Filling the details for the method
    }

    public String cardDescription()                     //Method to show the description for the card
    {
        String description = "";
        String x = getName();
        if(x.equals("The Mineralogist"))
        {description = "Change trump category to cleavage";}
        else if(x.equals("The Geologist"))
        {description = "Change to trump category of your choice";}
        else if(x.equals("The Geophysicist"))
        {description = "Change trump category to specific gravity or throw magnetite";}
        else if(x.equals("The Petrologist"))
        {description = "Change trump category to crustal abundance";}
        else if(x.equals("The Miner"))
        {description = "Change trump category to economic value";}
        else if(x.equals("The Gemmologist"))
        {description = "Change trump category to hardness";}
        return description;                                 //Filling the details for the method to describe
    }
}
