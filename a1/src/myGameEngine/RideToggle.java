package myGameEngine;
import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class RideToggle extends AbstractInputAction {
	private MyGame mygame;
	public RideToggle(MyGame g) {
		mygame = g;
	}
	@Override
	public void performAction(float time, Event e) {
		if(mygame.toggle == false) {
			mygame.toggle = true;
			mygame.camera.setMode('c');
			
				//----------------------------HANDLE DISMOUNT//
				Vector3 dolphinLocation = mygame.dolphinN.getLocalPosition();
				Vector3f p1 = (Vector3f)Vector3f.createFrom(0.4f, -0.2f, 0.2f); 
				Vector3f p2 = (Vector3f)dolphinLocation.add((Vector3)p1);
				mygame.camera.setPo((Vector3f)p2);
				//-------------------------------------------//
				
			System.out.println("DOLPHIN CAM DISABLED");	
		}else{
			mygame.toggle = false;
			mygame.camera.setMode('n');
			System.out.println("DOLPHIN CAM ENABLED");
		}
	}
}
