package crunch.color;

public class Gradient
{
  public Color startColor, endColor;
  
  public Gradient ()
  {
	  this(Color.randomColorNoAlpha(), Color.randomColorNoAlpha());
  }
  
  public Gradient (long startCol, long endCol)
  {
    setColor(startCol, endCol);
  }
  
  public void setColor(long startCol, long endCol)
  {
    startColor = new Color(startCol);
    endColor   = new Color(endCol);    
  }
  
  public Gradient (Color startCol, Color endCol)
  {
    startColor = startCol;
    endColor   = endCol;
  }
  
  public Color getColor(float amount)
  {
	  long r = startColor.red   + Math.round((float)(endColor.red   - startColor.red)   * amount);
	  long g = startColor.green + Math.round((float)(endColor.green - startColor.green) * amount);
	  long b = startColor.blue  + Math.round((float)(endColor.blue  - startColor.blue)  * amount);
	  long a = startColor.alpha + Math.round((float)(endColor.alpha - startColor.alpha) * amount);    
    
    return new Color(r, g, b, a);
  }
}