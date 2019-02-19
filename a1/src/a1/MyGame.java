package a1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import myGameEngine.*;
import net.java.games.input.Controller;
import ray.input.GenericInputManager;
import ray.input.InputManager;
import ray.input.action.Action;
import ray.rage.*;
import ray.rage.asset.material.Material;
import ray.rage.asset.texture.Texture;
import ray.rage.asset.texture.TextureManager;
import ray.rage.game.*;
import ray.rage.rendersystem.*;
import ray.rage.rendersystem.Renderable.*;
import ray.rage.scene.*;
import ray.rage.scene.Camera.Frustum.*;
import ray.rage.scene.Camera;
import ray.rage.scene.controllers.*;
import ray.rml.*;
import ray.rage.rendersystem.gl4.GL4RenderSystem;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import myGameEngine.*;

public class MyGame extends VariableFrameRateGame {
	public  Camera      camera;
	public  Camera      dCam;
	private Renderable  linSec;
	private RenderState renderState;
	GL4RenderSystem rs;
	float elapsTime = 0.0f;
	int   score     = 0;
	String elapsTimeStr, counterStr, dispStr, scoreStr;
	int elapsTimeSec, counter = 0;
	
	private InputManager im;
	private Action cameraForward, cameraBackward, cameraLeft, cameraRight, cameraUp, cameraDown,
	
				   cameraPitchUp, cameraPitchDown, cameraPanLeft, cameraPanRight,
				   
				   cameraY_C, cameraX_C,
				   
				   cameraPan, cameraPitch,
				   
				   barrellRollRight, barrellRollLeft,
				   
				   rideToggle;
	
	public boolean      toggle = false;
	public SceneNode    cameraNode;
	public SceneManager sm;
	public SceneNode    dolphinN;
	public  Entity      dolphin;
	
    public MyGame() {
        super();
    }

    public static void main(String[] args) {
        Game game = new MyGame();
        try {
            game.startup();
            game.run();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally { 
            game.shutdown();
            game.exit();
        }
    }
	
	@Override
	protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge) {
		rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
	}

    @Override
    protected void setupCameras(SceneManager sm, RenderWindow rw) {
        SceneNode rootNode = sm.getRootSceneNode();
        camera = sm.createCamera("MainCamera", Projection.PERSPECTIVE);
        rw.getViewport(0).setCamera(camera);
		
		camera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		camera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		camera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
		
		camera.setPo((Vector3f)Vector3f.createFrom(0.0f, 0.0f, 0.0f));

        cameraNode = rootNode.createChildSceneNode(camera.getName() + "Node");
        cameraNode.attachObject(camera);
    }
	
    @Override
    protected void setupScene(Engine eng, SceneManager sm) throws IOException {
        setupInputs();
    	
    	Entity dolphinE = sm.createEntity("myDolphin", "dolphinHighPoly.obj");
        dolphinE.setPrimitive(Primitive.TRIANGLES);
        
        dolphinN = sm.getRootSceneNode().createChildSceneNode(dolphinE.getName() + "Node");
        dolphinN.moveBackward(2.0f);
        dolphinN.attachObject(dolphinE);
        dolphinN.attachObject(camera);
        
//~~~~~~~~~~~~~~~~~~~~~~~~~MAKE CAMERA~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
        SceneNode dolphinCam = dolphinN.createChildSceneNode("myDolphin");

        dolphinCam.moveUp(0.3f);
        dolphinCam.moveBackward(0.299f);
        dolphinCam.moveRight(0.01f);
        dolphinCam.attachObject(camera);
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
//~~~~~~~~~~~~~~~~~~~~~~~~~~MAKE PLANETS~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//        
        Entity planet0E = sm.createEntity("planet0", "sphere.obj");
        planet0E.setPrimitive(Primitive.TRIANGLES);
        
        Entity planet1E = sm.createEntity("planet1", "sphere.obj");
        planet1E.setPrimitive(Primitive.TRIANGLES);
        
        Entity planet2E = sm.createEntity("planet2", "sphere.obj"); 
        planet2E.setPrimitive(Primitive.TRIANGLES);
        
        SceneNode planet0N = sm.getRootSceneNode().createChildSceneNode(planet0E.getName() + "Node");
        SceneNode planet1N = sm.getRootSceneNode().createChildSceneNode(planet1E.getName() + "Node");
        SceneNode planet2N = sm.getRootSceneNode().createChildSceneNode(planet2E.getName() + "Node");
        
        Material     planet0Mat = sm.getMaterialManager().getAssetByPath("asteroid_1.mtl");
                     planet0Mat.setEmissive(Color.CYAN);
        Texture      planet0Tex = sm.getTextureManager().getAssetByPath("moon.jpeg");
        TextureState p0TexState = (TextureState)sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
        p0TexState.setTexture(planet0Tex);

        Material     planet1Mat = sm.getMaterialManager().getAssetByPath("asteroid_2.mtl");
        planet0Mat.setEmissive(Color.MAGENTA);
        Texture      planet1Tex = sm.getTextureManager().getAssetByPath("earth-day.jpeg");
        TextureState p1TexState = (TextureState)sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
        p1TexState.setTexture(planet1Tex);

        Material     planet2Mat = sm.getMaterialManager().getAssetByPath("asteroid_3.mtl");
        planet0Mat.setEmissive(Color.GREEN);
        Texture      planet2Tex = sm.getTextureManager().getAssetByPath("earth-night.jpeg");
        TextureState p2TexState = (TextureState)sm.getRenderSystem().createRenderState(RenderState.Type.TEXTURE);
        p2TexState.setTexture(planet2Tex);
        
        planet0N.moveDown(10.5f);
        planet0N.moveBackward(9.5f);
        planet0N.attachObject(planet0E);
        planet0E.setRenderState(p0TexState);
        planet0E.setMaterial(planet0Mat);
        
        planet1N.moveUp(12.5f);
        planet1N.moveLeft(11.5f);
        planet1N.attachObject(planet1E);
        planet1E.setRenderState(p1TexState);
        planet1E.setMaterial(planet1Mat);
        
        planet2N.moveDown(7.5f);
        planet2N.moveRight(6.5f);
        planet2N.attachObject(planet2E);
        planet2E.setRenderState(p2TexState);
        planet2E.setMaterial(planet2Mat);;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//        
        
//~~~~~~~~~~~~~~~~~~~~~~~~~~~SETUP AXES~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
   
                                                                              
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
        TextureManager tm = eng.getTextureManager();
        Texture redTexture = tm.getAssetByPath("hexagons.jpeg");
        RenderSystem rs = sm.getRenderSystem();
        TextureState state = (TextureState)
        rs.createRenderState(RenderState.Type.TEXTURE);
        state.setTexture(redTexture);
        dolphinE.setRenderState(state);
        
        
        sm.getAmbientLight().setIntensity(new Color(.1f, .1f, .1f)); //AMBIENT
		Light plight = sm.createLight("testLamp1", Light.Type.POINT);//POSITIONAL V
		plight.setAmbient(new Color(.3f, .3f, .3f));
        plight.setDiffuse(new Color(.7f, .7f, .7f));
		plight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
        plight.setRange(5f);
		
		SceneNode plightNode = sm.getRootSceneNode().createChildSceneNode("plightNode");
        plightNode.attachObject(plight);
        
    }

    @Override
    protected void update(Engine engine) {
		// build and set HUD
		rs = (GL4RenderSystem) engine.getRenderSystem();
		elapsTime   += engine.getElapsedTimeMillis();
		elapsTimeSec = Math.round(elapsTime/1000.0f); 
		elapsTimeStr = Integer.toString(elapsTimeSec);
		counterStr   = Integer.toString(counter);
		dispStr      = "Time:   " + elapsTimeStr + "   SCORE:  ";
		rs.setHUD(dispStr, 15, 15);
		
		im.update(elapsTime);
	}

    @Override
    public void keyPressed(KeyEvent e) {
        dolphin = getEngine().getSceneManager().getEntity("myDolphin");
        switch (e.getKeyCode()) {
            case KeyEvent.VK_L:
                dolphin.setPrimitive(Primitive.LINES);
                break;
            case KeyEvent.VK_T:
                dolphin.setPrimitive(Primitive.TRIANGLES);
                break;
            case KeyEvent.VK_P:
                dolphin.setPrimitive(Primitive.POINTS);
                break;
        }
        super.keyPressed(e);
    }
    
    protected void setupInputs() {
    	im = new GenericInputManager();
    	ArrayList<Controller> controllers = im.getControllers();
    	
    	cameraForward     = new MoveForwardAction    (camera, this);
    	cameraBackward    = new MoveBackwardAction   (camera, this);
    	cameraLeft        = new MoveLeftAction       (camera, this);
    	cameraRight	      = new MoveRightAction      (camera, this);
    	
    	cameraPitchUp     = new PitchUpAction        (this);
    	cameraPitchDown   = new PitchDownAction      (this);
    	cameraPanLeft     = new PanLeftAction	     (this);
    	cameraPanRight    = new PanRightAction       (this);
    	
//_________________________HANDLE CONTROLLER________________________//
    	cameraY_C   = new MoveYAction_C  (camera, this);
    	cameraX_C   = new MoveXAction_C  (camera, this);
    	cameraPitch = new PitchUDAction_C    (this);
    	cameraPan   = new PanLRAction_C      (this);
//__________________________________________________________________//
    	
    	cameraUp          = new MoveUpAction	     (camera, this);
    	cameraDown        = new MoveDownAction       (camera, this);
    	
    	barrellRollRight  = new BarrelRightAction    (this);
    	barrellRollLeft   = new BarrelLeftAction     (this);
    	
    	rideToggle        = new RideToggle           (this);
    	
    for(Controller c : controllers) {
    	if(c.getType() == Controller.Type.KEYBOARD) {
    //~~~~~~~~~~~~~~~~~~~~~Keyboard Stuff~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.W,     cameraForward,
        			   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.A,     cameraLeft,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.S,     cameraBackward,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.D,     cameraRight,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.UP,    cameraPitchUp,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.DOWN,  cameraPitchDown,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.LEFT,  cameraPanLeft,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.RIGHT, cameraPanRight,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.E,     cameraUp,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.Q,     cameraDown,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key.SPACE, rideToggle,
    				   InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key._1,    barrellRollRight,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	im.associateAction(c, net.java.games.input.Component.Identifier.Key._4,    barrellRollLeft,
    				   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        	
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    	}	else if(c.getType() == Controller.Type.GAMEPAD || c.getType() == Controller.Type.STICK) {
    //~~~~~~~~~~~~~~~~~~~~~GamePad Stuff~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    	    im.associateAction(c, net.java.games.input.Component.Identifier.Axis.Y,     cameraY_C,
 	 			       InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
 	 	    im.associateAction(c, net.java.games.input.Component.Identifier.Axis.X,     cameraX_C,
 					   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
 	 	    im.associateAction(c, net.java.games.input.Component.Identifier.Axis.RY,    cameraPan,
 					   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
 	 	    im.associateAction(c, net.java.games.input.Component.Identifier.Axis.RX,    cameraPitch,
 					   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
 	 	    im.associateAction(c, net.java.games.input.Component.Identifier.Button._9,  cameraUp,         //RB
 					   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
 	 	    im.associateAction(c, net.java.games.input.Component.Identifier.Button._10, cameraDown,       //LB
 					   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
 	 	    im.associateAction(c, net.java.games.input.Component.Identifier.Button._1,  rideToggle,       //A
 					   InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
 	 	    im.associateAction(c, net.java.games.input.Component.Identifier.Button._6,  barrellRollRight, //RT
 					   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
 	 	    im.associateAction(c, net.java.games.input.Component.Identifier.Button._5,  barrellRollLeft,  //LT
 					   InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN); 
 	 	    
 	 	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//
    	}
      }
   }
    		
}

