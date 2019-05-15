package dk.Jonas.org.graphics;

import dk.Jonas.org.graphics.ray.Ray;
import dk.Jonas.org.vector.Matrix4x4;
import dk.Jonas.org.vector.Vector3;

public class Camera {
	private int width, height;
	private double fov = 90;
	private double aspectRatio;
	private Vector3 rot = new Vector3(0, 0, 0);
	private Vector3 pos = new Vector3(0, 0, 0);;
	private Matrix4x4 rotationMatrix;
	
	public Camera(int width, int height, double fov) {
		this.width = width;
		this.height = height;
		this.fov = fov;
		aspectRatio = (double) width/height;
	}
	
	public Ray generateRay(int x, int y) {
		double hFov = fov / aspectRatio;

		double wp = (double) x / width;
		double hp = (double) y / height;

		double rotY = fov * (wp-0.5);
		double rotX = hFov * (hp-0.5);
		
		Vector3 rayDir = new Vector3(0, 0, 1);
		
		rayDir = rayDir.mul(new Matrix4x4(1).rotateMatrix(rotX, rotY, 0).mul(rotationMatrix));
		
		rayDir.normalize();
		
		return new Ray(new Vector3(pos.x, pos.y, pos.z), rayDir);
	}
	
	public void updateRotation() {
		rotationMatrix = new Matrix4x4(1).rotateMatrix(rot);
	}
}
