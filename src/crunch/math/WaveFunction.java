package crunch.math;

import crunch.flow.LFO;

public class WaveFunction implements Function
{
  private float amp, lon; 
  private Vertex3D v;
  
  private float curPhase, time;
  
  private LFO lfoSC;
  
  public WaveFunction(Vertex3D v, float amp, float lon, float time)
  {
    this.v = v;
    this.lon = lon;
    this.amp = amp;
    this.time = time;
    curPhase = 0;
  }
  
  public void tick(int ms)
  {
    //this.sc = lfoSC.tick(ms);
    curPhase += ms;
  }
  
  public void lfo(int lon, int amp, int mid, float phase){};
  
  public float getVal(Vertex3D v)
  {
    float distance = v.getSphericDist(this.v);
    
    float fac = distance;
    
    if (fac < 5) fac = 5;
    
    return amp * (float)Math.sin(distance / lon * Math.PI*2 + (float)curPhase/ time * Math.PI*2) / (float)Math.sqrt(fac);
  }
}