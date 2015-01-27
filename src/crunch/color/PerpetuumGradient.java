package crunch.color;

public class PerpetuumGradient
{
	public Gradient gradOne, gradTwo;

	public int lonOne, lonTwo, curLonOne, curLonTwo;

  public PerpetuumGradient()
  {
	  
	  
	  
    gradOne = new Gradient(Color.randomColorRange(0, 0xFF, 0, 0xFF, 0, 0xFF, 0, 0x77), 
    					   Color.randomColorRange(0, 0xFF, 0, 0xFF, 0, 0xFF, 0, 0x77));
    gradTwo = new Gradient(Color.randomColorRange(0, 0xFF, 0, 0xFF, 0, 0xFF, 0x00, 0x00), 
			   			   Color.randomColorRange(0, 0xFF, 0, 0xFF, 0, 0xFF, 0x00, 0x00));/*
	  gradOne = new Gradient(Color.randomShadeOfGrey(), 
			  Color.randomShadeOfGrey());
gradTwo = new Gradient(Color.randomShadeOfGrey(), Color.randomShadeOfGrey());   
    */
    lonOne = (int)Math.round(Math.random() * 4000 + 1000);
    lonTwo = (int)Math.round(Math.random() * 4000 + 1000);  

    curLonOne = 0;
    curLonTwo = 0;
  }
  
  public Gradient next(int ms)
  {
    if (curLonOne >= lonOne)
    {
        gradOne = new Gradient(gradOne.endColor, Color.randomColorRange(0, 0xFF, 0, 0xFF, 0, 0xFF, 0x00, 0x44));
     // gradOne = new Gradient(Color.randomShadeOfGrey(), Color.randomShadeOfGrey());
      curLonOne -= lonOne;
    }

    if (curLonTwo >= lonTwo)
    {
        gradTwo = new Gradient(gradTwo.endColor, Color.randomColorRange(0, 0xFF, 0, 0xFF, 0, 0xFF, 0x00, 0x44));
     // gradTwo = new Gradient(Color.randomShadeOfGrey(), Color.randomShadeOfGrey());   
      curLonTwo -= lonTwo;
    }
    
    curLonOne += ms;
    curLonTwo += ms;
    
    float amountOne = (float)curLonOne / (float) lonOne;
    float amountTwo = (float)curLonTwo / (float) lonTwo;
    
    return new Gradient(gradOne.getColor(amountOne), gradTwo.getColor(amountTwo));
  }
}