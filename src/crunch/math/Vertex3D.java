package crunch.math;

public class Vertex3D
{
	public float x,y,z;
 public float r, rho, phi;

 public float lon, lat;

 public Vertex3D(float x, float y, float z, boolean isCartesian)
 {
   if (isCartesian)
   {
      this.x = x;
      this.y = y;
      this.z = z;
      getSpheric(); 
   }
   else
   {
      this.r = x;
      this.rho = y;
      this.phi = z;
      
      getLonLat();      
      getCartesian();
   }
 }
 
 public void setR(float r)
 {
  this.r = r;
  getCartesian(); 
 }
 
 public void setRho(float rho)
 {
  this.rho = rho;
  getCartesian(); 
 }
 
 public void setPhi(float phi)
 {
  this.phi = phi;
  getCartesian(); 
 }
 
 public void getSpheric()
 {
   r = (float)Math.sqrt((double)( x*x + y*y + z*z) );
   if (y >= 0) rho = (float)(Math.acos( x / Math.sqrt( x*x + y*y ) ));
   else rho = (float)(Math.PI*2 - Math.acos( x / Math.sqrt( x*x + y*y ) ));
   phi = (float)(Math.PI/2 - Math.atan( z / Math.sqrt( x*x + y*y ) ));
   
   getLonLat();
 }
 
 public void getCartesian()
 {
   x = (float)(r * Math.sin( (double)phi ) * Math.cos( (double)rho ));
   y = (float)(r * Math.sin( (double)phi ) * Math.sin( (double)rho ));
   z = (float)(r * Math.cos( (double)phi ));
 }
 
 public void getLonLat()
 {
   this.lon = - (float)( Math.PI*2 - rho );
   this.lat = (float)(phi - Math.PI/2); 
 }
 
 public float getSphericDist(Vertex3D v)
 {
    return (float)(r * Math.acos(Math.sin(v.lat)*Math.sin(lat) + Math.cos(v.lat)*Math.cos(lat)*Math.cos(lon - v.lon)));
 }
 
 
}