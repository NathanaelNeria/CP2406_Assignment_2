/**
 * Created by Rico on 1/4/2017.
 */
public class NormalCard extends Card {              //Class extend from the Card Class
    private float hardness;
    private float specificGravity;
    private String cleavage;
    private int cleavageValue;
    private String crustalAbundance;
    private int crustalAbundanceValue;
    private String ecoValue;
    private int ecoValueValue;              //Defining all the variable

    NormalCard(String nm, float hard, float speGra, String cleav, String cryAbu, String ecoV)
    {
        super(nm);
        hardness = hard;
        specificGravity = speGra;
        cleavage = cleav;
        crustalAbundance = cryAbu;
        ecoValue = ecoV;
        cleavageValue = convertCleavageValue();
        crustalAbundanceValue = convertAbuValue();
        ecoValueValue = convertEcoValue();                  //Constructor for all the variable
    }

    public int getCleavageValue() {
        return cleavageValue;
    }

    public int getCrustalAbundanceValue() {
        return crustalAbundanceValue;
    }

    public int getEcoValueValue() {
        return ecoValueValue;
    }

    public float getHardness() {
        return hardness;
    }

    public float getSpecificGravity() {
        return specificGravity;
    }

    public String getCleavage() {
        return cleavage;
    }

    public String getCrustalAbundance() {
        return crustalAbundance;
    }

    public String getEcoValue() {
        return ecoValue;
    }                           //All the get method for all the variable

    public int convertCleavageValue()                                           //Converting all the Cleavage into int number to be compared
    {
        int cleaValue = 0;
        String x = getCleavage();
        if(x.equals("none"))
        {cleaValue = 1;}
        else if(x.equals("poor/none"))
        {cleaValue = 2;}
        else if(x.equals("1 poor"))
        {cleaValue = 3;}
        else if(x.equals("2 poor"))
        {cleaValue = 4;}
        else if(x.equals("1 good"))
        {cleaValue = 5;}
        else if(x.equals("1 good/1 poor"))
        {cleaValue = 6;}
        else if(x.equals("2 good"))
        {cleaValue = 7;}
        else if(x.equals("3 good"))
        {cleaValue = 8;}
        else if(x.equals("1 perfect"))
        {cleaValue = 9;}
        else if(x.equals("1 perfect/1 good"))
        {cleaValue = 10;}
        else if(x.equals("1 perfect/2 good"))
        {cleaValue = 11;}
        else if(x.equals("2 perfect/1 good"))
        {cleaValue = 12;}
        else if(x.equals("3 perfect"))
        {cleaValue = 13;}
        else if(x.equals("4 perfect"))
        {cleaValue = 14;}
        else if(x.equals("6 perfect"))
        {cleaValue = 15;}
        return cleaValue;
    }
    public int convertAbuValue()                    //Converting all the Crustal Abundance into int to be compared
    {
        int aValue = 0;
        String x = getCrustalAbundance();
        if(x.equals("ultratrace"))
        {aValue=1;}
        else if(x.equals("trace"))
        {aValue=2;}
        else if(x.equals("low"))
        {aValue=3;}
        else if(x.equals("moderate"))
        {aValue=4;}
        else if(x.equals("high"))
        {aValue=5;}
        else if(x.equals("very high"))
        {aValue=6;}
        return aValue;
    }
    public int convertEcoValue()                //Converting the economic value into int to be compared
    {
        int eValue = 0;
        String x = getEcoValue();
        if(x.equals("trivial"))
        {eValue=1;}
        else if(x.equals("low"))
        {eValue=2;}
        else if(x.equals("moderate"))
        {eValue=3;}
        else if(x.equals("high"))
        {eValue=4;}
        else if(x.equals("very high"))
        {eValue=5;}
        else if(x.equals("I'm rich!"))
        {eValue=6;}
        return eValue;
    }
}
