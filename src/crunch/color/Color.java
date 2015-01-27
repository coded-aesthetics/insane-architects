package crunch.color;

public class Color
{
 public long red, green, blue, alpha;
 
 public long col;

 public Color(long col)
 {
  this.col = col;
  setColor(col); 
 }
 
 public Color(long r, long g, long b, long a)
 {
  red = r;
  green = g;
  blue = b;
  alpha = a; 
  
  this.col = 0x01000000 * alpha + 0x00010000 * red + 0x00000100 * green + blue;
 }

 public void setColor(long col)
 {
    alpha  = (col & 0xFF000000) >> 24;
    red    = (col & 0x00FF0000) >> 16;
    green  = (col & 0x0000FF00) >>  8; 
    blue   = (col & 0x000000FF);       
 } 

 public String toString()
 {
	 return "[a] " + alpha + ", [r] " + red + ", [g] " + green + ", [b] " + blue; 
 }
 
 public static Color randomShadeOfGrey()
 {
	 int tint = (int)(Math.round(Math.random() * 255));
	 return new Color(tint, tint, tint, 0);
 }
 
 public static Color randomColor()
 {
	 return new Color(Math.round(Math.random() * (1l << 32)));
 }

 public static Color randomColorNoAlpha()
 {
	 return new Color(Math.round(Math.random() * (1l << 24)) | 0xFF000000);
 }

 public static Color randomColorRange(long rmin, long rmax, long gmin, long gmax, long bmin, long bmax, long amin, long amax) {
	 long r = (long)Math.round(Math.random() * (rmax - rmin)) + rmin;
	 long g = (long)Math.round(Math.random() * (gmax - gmin)) + gmin;
	 long b = (long)Math.round(Math.random() * (bmax - bmin)) + bmin;
	 long a = (long)Math.round(Math.random() * (amax - amin)) + amin;
	 
	 return new Color(r, g, b, a);
 }
 
}
