package crunch.math;

import crunch.flow.LFO;

public class EFunction implements Function
{
  float sigma, sc; 
  Vertex3D v;
  
  private LFO lfoSC;
  
  public EFunction(Vertex3D v, float sigma, float sc)
  {
    this.v = v;
    this.sigma = sigma;
    this.sc = sc;
  }
  
  public void lfo(int lon, int amp, int mid, float phase)
  {
     this.lfoSC = new LFO(lon, amp, mid, phase);
  }
  
  public void tick(int ms)
  {
    this.sc = lfoSC.tick(ms);
  }
  
  public float getVal(Vertex3D v)
  {
    float distance = v.getSphericDist(this.v);
    
    if (distance > sigma*sigma) return 0;
    
    return sc * (float)Math.exp(-(distance*distance / (2.0*sigma*sigma)));
  }
}
