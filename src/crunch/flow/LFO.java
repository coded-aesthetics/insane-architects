package crunch.flow;

public class LFO
{
  public float lon, amp, mid, phase;
  
  public int timePassed;
  
  public LFO(float lon, float amp, float mid, float phase)
  {
    this.lon = lon;
    this.amp = amp;
    this.mid = mid;
    this.phase = phase;
    
    timePassed = 0;
  }
  
  public float tick(int ms)
  {
    float ret = (float)Math.sin(timePassed / lon * (float)Math.PI*2 + phase) * amp + mid;
    timePassed += ms;
    return ret;
  }
  
  public void reset(int timePassed)
  {
   this.timePassed = timePassed; 
  }
}