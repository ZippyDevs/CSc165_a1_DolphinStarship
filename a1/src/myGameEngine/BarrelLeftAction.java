package myGameEngine;

import a1.MyGame;
import net.java.games.input.Event;
import ray.input.action.AbstractInputAction;
import ray.rml.Angle;
import ray.rml.Degreef;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class BarrelLeftAction extends AbstractInputAction{
	private MyGame mygame;
	
	public BarrelLeftAction(MyGame g) {
		mygame = g;
	}
	
	@Override
	public void performAction(float time, Event e) {
		if(mygame.camera.getMode()=='n') {
		       Angle rotAmt = Degreef.createFrom(-3.0f);
		       mygame.dolphinN.roll(rotAmt);
		       
		       System.out.println("DO A BARRELL ROLL!!! || MODE : 'N'");
		}else {
		       Angle   rotAmt  = Degreef.createFrom(-3.0f);
		       
		       Vector3 uVector = mygame.camera.getRt();
		       Vector3 vVector = mygame.camera.getUp();
		       Vector3 nVector = mygame.camera.getFd();
		       
		       Vector3 uTransform = (uVector.rotate(rotAmt, nVector)).normalize();
		       Vector3 vTransform = (vVector.rotate(rotAmt, nVector)).normalize();

		       mygame.camera.setRt((Vector3f)uTransform);
		       mygame.camera.setUp((Vector3f)vTransform);
		       
		       System.out.println("DO A BARRELL ROLL!!! || MODE : 'C'");
		}
		
	}
}

