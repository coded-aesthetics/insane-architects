package crunch.insanearchitects;

import processing.core.PApplet;
import processing.core.PImage;
import crunch.color.Color;
import crunch.color.Gradient;
import crunch.color.PerpetuumGradient;
import crunch.flow.LFO;
import crunch.math.EFunction;
import crunch.math.Function;
import crunch.math.Vertex3D;
import crunch.math.WaveFunction;

public class InsaneArchitectsSketch extends PApplet{
	/** On for the bureaucracy*/
	private static final long serialVersionUID = 1L;
	
	public static void main(String args[]) {
	    PApplet.main(new String[] { "--present", "crunch.insanearchitects.InsaneArchitectsSketch" });
	  }
	
	/***************************************
	**        VARIABLES // CONSTANTS      **
	****************************************/
	/** Amount of Sine-Wave functions that are to be mapped on the sphere */
	private static final int NUM_WAVE_FUNCTIONS = 10;
	/** Amount of Gauss-functions that are to be mapped on the sphere */
	private static final int NUM_E_FUNCTIONS    = 0;

	/** Amount of horizontal sphere-vertices */
	private static final int NUM_FACES_HOR = 40;
	/** Amount of vertical sphere-vertices */	
	private static final int NUM_FACES_VER = 40;
	
	/** scale factor */
	private static final float SCALE = 0.8f;
	
	/** radius of the sphere */
	private static final float SPHERE_RADIUS = 200 * SCALE;

	
	/**********************************
	 ** Limits for the random values **
	 **********************************/
	
	/** minimum size of the gauss functions */
	private static final float MIN_E_SCALE = 50.0f * SCALE;
	/** maximum size of the gauss functions */
	private static final float MAX_E_SCALE = 100f * SCALE;

	/** minimum sigma of the gauss functions */
	private static final float MIN_E_SIGMA = 50.0f * SCALE;
	/** maximum sigma of the gauss functions */	
	private static final float MAX_E_SIGMA = 75.0f * SCALE;

	/** minimum oscillation-frequency of the gauss-functions */
	private static final float MIN_E_TIME = 4000;
	/** maximum oscillation-frequency of the gauss-functions */
	private static final float MAX_E_TIME = 8000;

	/** minimum amplitude for the sine-functions */	
	private static final float MIN_WAVE_AMP = 50 * 4;
	/** maximum amplitude for the sine-functions */	
	private static final float MAX_WAVE_AMP = 90 * 4;

	/** minimum wavelength for the sine-functions */
	private static final float MIN_WAVE_LON = 90;
	/** maximum wavelength for the sine-functions */	
	private static final float MAX_WAVE_LON = 180;

	/** minimum oscillation-frequency of the sine-functions */
	private static final float MIN_WAVE_TIME = 1500;
	/** maximum oscillation-frequency of the sine-functions */
	private static final float MAX_WAVE_TIME = 3000;

	/** timestamp that marks the last time the draw-function has been called */
	private int lastTime = 0;

	/** This is used to generate a continuous smooth transition between random colors */
	private PerpetuumGradient pg;
	
	/** Storage for the current vertices of the sphere */
	private Vertex3D [][] sphereVertices;
 
	/** Storage for the state of the functions mapped on the sphere */
	private Function[] funcs;
	/** Texture image */
	private PImage img;

	/** Flag to determine whether user is currently allowed to rotate the sphere */
	private boolean mouseControlEnabled = false;

	/** Initial offset for the horizontal rotation of the sphere */
	private float startAngX = 0.6f;
	/** Initial offset for the vertical rotation of the sphere */	
	private float startAngY = 0.0f;

	/** Current horizontal rotation angle */
	private float angY = startAngY;
	/** Current vertical rotation angle */
	private float angX = startAngX;
	
	/** Variables for mouse control */
	private float offsetX = 0;
	private float offsetY = 0;
	private float lastMouseX = 0;
	private float lastMouseY = 0;

	/****************************************************************
	****        BEGIN OF FUNCTIONS          *************************
	****************************************************************/

	
	
	/**
	 * Invoked when user releases a mouse-button
	 */
	public void mouseReleased()
	{
	  mouseControlEnabled = !mouseControlEnabled;
	  if (!mouseControlEnabled)
	  {
	    lastMouseX = mouseX - offsetX;
	    lastMouseY = mouseY - offsetY;
	  }
	  else
	  {
	   offsetX = mouseX - lastMouseX;
	   offsetY = mouseY - lastMouseY;
	  }
	}

	/**
	 * The Current Texture of the sphere is generated here
	 */
	private void drawTexture()
	{
	  Gradient g = pg.next(millis() - lastTime);
	  LFO lfo = new LFO(120, 0.5f, 0.5f, 0);
	  
	  img = createImage(120, 1, ARGB);
	  float amount = 0;
	  for(int i=0; i < img.pixels.length; i++) {
	    amount = lfo.tick(1);
	    Color c = g.getColor(amount);
	    img.pixels[i] = color(c.red, c.green, c.blue, c.alpha); 
	  }
	}

	/**
	 * Processing setup-function
	 */
	public void setup() {
	  size(407, 482, OPENGL);
	  
	  pg = new PerpetuumGradient();
	  drawTexture();
	  
	  funcs = new Function[NUM_E_FUNCTIONS + NUM_WAVE_FUNCTIONS];
	  
	  for (int i = 0; i < NUM_E_FUNCTIONS; i++)
	  {
	    funcs[i] = new EFunction(new Vertex3D(SPHERE_RADIUS, random(0, PApplet.TWO_PI), 
	    		            random(PApplet.PI/12, 11*PApplet.PI/12), false), random(MIN_E_SIGMA, MAX_E_SIGMA), random(35, 70)); 
	    funcs[i].lfo(round(random(MIN_E_TIME, MAX_E_TIME)), round(random(MIN_E_SCALE, MAX_E_SCALE)), 0, random(0, TWO_PI));
	  }
	  
	  for (int i = NUM_E_FUNCTIONS; i < NUM_WAVE_FUNCTIONS + NUM_E_FUNCTIONS; i++)
	  {
	    funcs[i] = new WaveFunction(new Vertex3D(SPHERE_RADIUS, random(0, TWO_PI), random(PI/12, 11*PI/12), false), random(MIN_WAVE_AMP, MAX_WAVE_AMP), random(MIN_WAVE_LON, MAX_WAVE_LON), random(MIN_WAVE_TIME, MAX_WAVE_TIME)); 
	  }
	  
	  lastTime = millis();
	  
	  buildSphere();
	}

	/**
	 * Processing draw-function
	 * This is called continuously throughout the runtime of the sketch
	 */
	public void draw() {
	  drawTexture();
	  background(255);
	  lights();
	  
	  translate(width / 2, height / 2, -350);
	  
	  if (mouseControlEnabled)
	  {
	    angY = map(mouseX - offsetX, 0, width, 0, 3*PI) + startAngY;
	    angX = map(mouseY - offsetY, 0, height, 0, -3*PI) + startAngX;
	  }
	  
	  rotateY(angY);
	  rotateX(angX);

	  noStroke();
	  fill(255, 255, 255);
	  translate(0, -40, 0);
	  
	  for (int i = 0; i < NUM_WAVE_FUNCTIONS + NUM_E_FUNCTIONS; i++) {
	    funcs[i].tick(millis() - lastTime);
	  }
	  
	  lastTime = millis();

	  addFuncs();  

	  drawSphere(NUM_FACES_HOR, NUM_FACES_VER);
	}

	/**
	 * Here the sphere without mapped functions is created.
	 */
	private void buildSphere()
	{
	  // amount of degrees the horizontal angle is to be incremented every iteration
	  float inc_hor = TWO_PI / (float)NUM_FACES_HOR;
	  // amount of degrees the vertical angle is to be incremented every iteration
	  float inc_ver = PI / (float)NUM_FACES_VER;  
	  // current horizontal angle
	  float angle_horizontal = 0;
	  // current vertical angle
	  float angle_vertical = 0;
	  
	  // init storage-array for the sphere vertices
	  sphereVertices = new Vertex3D[NUM_FACES_VER + 2][];
	  for (int i = 0; i < NUM_FACES_VER + 2; i++)
	  {
	    angle_horizontal = 0;
	    
	    sphereVertices[i] = new Vertex3D[NUM_FACES_HOR + 1];
	    for (int j = 0; j < NUM_FACES_HOR + 1; j++)
	    {
	       float rnd = 0;
	       
	       // this constructs a new vertex using
	       // spherical coordinates, so only the horizontal and
	       // vertical angle have to be passed. Conversion to 
	       // cartesian coordinates is done in the Vertex3D class
	       sphereVertices[i][j] = new Vertex3D(SPHERE_RADIUS + rnd, angle_horizontal, angle_vertical, false);
	       angle_horizontal += inc_hor;
	    } 
	    
	    angle_vertical += inc_ver;
	  } 
	}
	
	/**
	 * Initializes the sphere and maps the functions to its surface
	 */
	private void addFuncs()
	{  
	  // init sphere
	  buildSphere();
	  
	  // map functions to the surface
	  for (int i = 0; i < NUM_WAVE_FUNCTIONS + NUM_E_FUNCTIONS; i++)
	     addFunc(funcs[i]); 
	}

	/**
	 * Maps a function on the sphere's surface
	 * @param f Function to be mapped on the surface
	 */
	private void addFunc(Function f)
	{
		  // amount of degrees the horizontal angle is to be incremented every iteration
		  float inc_hor = TWO_PI / (float)NUM_FACES_HOR;
		  // amount of degrees the vertical angle is to be incremented every iteration
		  float inc_ver = PI / (float)NUM_FACES_VER;  
		  // current horizontal angle
		  float angle_horizontal = 0;
		  // current vertical angle
		  float angle_vertical = 0;

	  for (int i = 0; i < NUM_FACES_VER + 1; i++)
	  {
	   angle_horizontal = 0; 
	   
	   for (int j = 0; j < NUM_FACES_HOR + 1; j++)
	   {
	     Vertex3D curVertex = new Vertex3D(SPHERE_RADIUS, angle_horizontal, angle_vertical, false);
	     
	     // Here the radius at the current sphere-vertex is changed according to
	     // the mapped function's value. The mapped functions are
	     // rotation-symmetric, so only the distance between the center
	     // and the current vertex is used to determine the function's value.
	     sphereVertices[i][j].setR(sphereVertices[i][j].r + f.getVal(curVertex));
	     angle_horizontal += inc_hor;
	   }
	   angle_vertical += inc_ver;
	  }
	}

	/**
	 * Draws the sphere that is stored in the sphereVertices array
	 * @param faces_hor number of horizontal vertices
	 * @param faces_ver number of vertical vertices
	 */
	private void drawSphere(int faces_hor, int faces_ver)
	{
	  // amount of degrees the vertical angle is to be incremented for each iteration
	  float inc_ver = PI / (float)faces_ver;
	  // current vertical angle
	  float angle_vertical = 0;
	  
	  // Here the sphere (with mapped functions) is drawn ring by ring 
	  for (int i = 0; i < faces_ver; i++)
	  {
	    beginShape(QUAD_STRIP);
	    texture(img);

	    drawRing(i, faces_hor, 0);
	       
	    endShape();
		
	    // draw a black ring every 6 rings.
	    // with an initial offset of 2 rings
	    // these rings protrude 50 degrees 
	    if (i % 6 == 2)
		{
	        beginShape(QUAD_STRIP);
	        img = createImage(1, 1, ARGB);
	        img.pixels[0] = color(0, 0, 0, 255);
		    texture(img);
		    
		    // draw the black ring that protrudes by 50 degrees
		    drawRing(i, faces_hor, 50);
		       
		    endShape();
		    drawTexture();
		}
	    angle_vertical += inc_ver;
	  }
	  
	}

	/**
	 * Here a single ring (as stored in the sphereVertices-array)
	 * of the sphere is drawn
	 * @param i index of the current ring
	 * @param faces_hor number of horizontal faces for the ring
	 * @param protrude static value added to the radius of the ring
	 */
	private void drawRing(int i, int faces_hor, int protrude)
	{
		// increment per interation
	    float inc_hor = TWO_PI / (float)faces_hor;
	    // current angle
	    float angle_horizontal = 0;
	    
	    // current vertices
	    Vertex3D curVertex, curVertex2;
	  
	    for (int s = 0; s < faces_hor + 1; s++) 
	    {
	       curVertex  = sphereVertices[i]    [s];
	       curVertex2 = sphereVertices[i + 1][s];
	       
	       if (curVertex.r < 0) curVertex.setR(0);
	       if (curVertex2.r < 0) curVertex2.setR(0);
	       
	       // protrude current vertices
	       curVertex.setR(curVertex.r + protrude);
	       curVertex2.setR(curVertex2.r + protrude);       
	       
	       float texCoordX = curVertex.r / (float)(SPHERE_RADIUS * 2.6);
	       if (texCoordX >= 0.97) texCoordX = 0.97f;
	       
	       float texCoordX2 = curVertex2.r / (float)(SPHERE_RADIUS * 2.6);
	       if (texCoordX2 >= 0.97) texCoordX2 = 0.97f;

	       vertex(curVertex.x,  curVertex.y,  curVertex.z, texCoordX, 0.5f);
	       vertex(curVertex2.x, curVertex2.y, curVertex2.z, texCoordX2, 0.5f);       
	       
	       angle_horizontal += inc_hor;
	    }
	    
	    curVertex  = sphereVertices[i]    [0];
	    curVertex2 = sphereVertices[i + 1][0];
	       
	    vertex(curVertex.x,  curVertex.y,  curVertex.z);//, (float) i / (float) (faces_ver + 1) * img.width, img.height);
	    vertex(curVertex2.x, curVertex2.y, curVertex2.z);//, (float) (i+1) / (float) (faces_ver + 1) * img.width, img.height);       
	  
	}
}
