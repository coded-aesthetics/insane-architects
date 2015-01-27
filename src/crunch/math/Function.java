package crunch.math;

public interface Function {
	 float getVal(Vertex3D v); 
	 void lfo(int lon, int amp, int mid, float phase);
	 void tick(int ms);
}
