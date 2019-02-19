package myGameEngine;

import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.Camera;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class MoveXAction_C extends AbstractInputAction {
	private Camera cam;
	private MyGame mygame;
	
	public MoveXAction_C(Camera c, MyGame g) {
		cam    = c;
		mygame = g;
	}
	@Override
	public void performAction(float time, Event e) {
		if(e.getValue() <= -0.2f) {
			if(mygame.camera.getMode() == 'c') {      // if the view mode is set to 'c' (OFF DOLPHIN)
				System.out.println("Moving Left || MODE: 'C'");
				Vector3f v   =   cam.getFd();
				Vector3f p   =   cam.getPo();
				Vector3f p1  =   (Vector3f)Vector3f.createFrom(0.15f*v.x(),0.15f*v.y(), 0.15f*v.z());
				Vector3f p2  =   (Vector3f)p.add((Vector3)p1);
				cam.setPo(       (Vector3f)Vector3f.createFrom(p2.x(), p2.y(),p2.z()));
			}else{                                    // if the view mode is set to 'n' (ON DOLPHIN)
				System.out.println("Moving Left || MODE: 'N'");
				mygame.dolphinN.moveForward(0.15f);
			}
		}else if(e.getValue() >= 0.2f) {
			if(mygame.camera.getMode() == 'c') { // if the view mode is set to 'c' (OFF DOLPHIN)
				System.out.println("Moving Right || MODE: 'C'");
				Vector3f v   =   cam.getFd();
				Vector3f p   =   cam.getPo();
				Vector3f p1  =   (Vector3f)Vector3f.createFrom(-0.15f*v.x(),-0.15f*v.y(), -0.15f*v.z());
				Vector3f p2  =   (Vector3f)p.add((Vector3)p1);
				cam.setPo(       (Vector3f)Vector3f.createFrom(p2.x(), p2.y(),p2.z()));
			}else {					     // if the view mode is set to 'n' (ON DOLPHIN)
				System.out.println("Moving Right || MODE: 'N'");
				mygame.dolphinN.moveBackward(0.15f);
			}
		}
	}
}