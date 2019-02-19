package myGameEngine;

import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class PanRightAction extends AbstractInputAction{
	private MyGame mygame;
	
	public PanRightAction(MyGame g) {
		mygame = g;
	}
	@Override
	public void performAction(float time, Event e) {
			if(mygame.camera.getMode()=='n') {
		       Angle rotAmt = Degreef.createFrom(-5.0f);
		       mygame.dolphinN.yaw(rotAmt);
		       
		       System.out.println("PANNING RIGHT || MODE : 'N'");       
			}else {
		       Angle   rotAmt  = Degreef.createFrom(-5.0f);
		       
		       Vector3 uVector = mygame.camera.getRt();
		       Vector3 vVector = mygame.camera.getUp();
		       Vector3 nVector = mygame.camera.getFd();
		       
		       Vector3 uTransform = (uVector.rotate(rotAmt, vVector)).normalize();
		       Vector3 nTransform = (nVector.rotate(rotAmt, vVector)).normalize();

		       mygame.camera.setRt((Vector3f)uTransform);
		       mygame.camera.setFd((Vector3f)nTransform);
		       
		       System.out.println("PANNING RIGHT || MODE : 'C'");       
			}
	  }
}
