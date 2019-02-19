package myGameEngine;

import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rml.*;
import a1.MyGame;

public class MoveForwardAction extends AbstractInputAction {
	private Camera cam;
	private MyGame mygame;
	
	public MoveForwardAction(Camera c, MyGame g) {
		cam    = c;
		mygame = g;
	}
	@Override
	public void performAction(float time, Event e) {
			if(mygame.camera.getMode() == 'c') {      // if the view mode is set to 'c' (OFF DOLPHIN)
				System.out.println("Moving Forward || MODE: 'C'");
				Vector3f v   =   cam.getFd();
				Vector3f p   =   cam.getPo();
				Vector3f p1  =   (Vector3f)Vector3f.createFrom(0.15f*v.x(),0.15f*v.y(), 0.15f*v.z());
				Vector3f p2  =   (Vector3f)p.add((Vector3)p1);
				cam.setPo(       (Vector3f)Vector3f.createFrom(p2.x(), p2.y(),p2.z()));
			}else{                                    // if the view mode is set to 'n' (ON DOLPHIN)
				System.out.println("Moving Forward || MODE: 'N'");
				mygame.dolphinN.moveForward(0.15f);
			}
		}
	}	
