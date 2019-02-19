package myGameEngine;

import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class PitchDownAction extends AbstractInputAction{
	private MyGame mygame;
	
	public PitchDownAction(MyGame g) {
		mygame = g;
	}
	
	@Override
	public void performAction(float time, Event e) {
			if(mygame.camera.getMode()=='n') {
		       Angle rotAmt = Degreef.createFrom(-5.0f);
		       mygame.dolphinN.pitch(rotAmt);
		       
		       System.out.println("PITCHING DOWN || MODE : 'N'");

			}else {
		       Angle   rotAmt  = Degreef.createFrom(5.0f);
		       
		       Vector3 uVector = mygame.camera.getRt();
		       Vector3 vVector = mygame.camera.getUp();
		       Vector3 nVector = mygame.camera.getFd();
		       
		       Vector3 vTransform = (vVector.rotate(rotAmt, uVector)).normalize();
		       Vector3 nTransform = (nVector.rotate(rotAmt, uVector)).normalize();

		       mygame.camera.setUp((Vector3f)vTransform);
		       mygame.camera.setFd((Vector3f)nTransform);
		       
		       System.out.println("PITCHING DOWN || MODE : 'C'");
			}
	  }
}
