package dk.Jonas.org.vector;

public class Matrix4x4 {
	double m00, m10, m20, m30;
	double m01, m11, m21, m31;
	double m02, m12, m22, m32;
	double m03, m13, m23, m33;
	
	public Matrix4x4(double m00, double m10, double m20, double m30, double m01, double m11, double m21, double m31, double m02, double m12, double m22, double m32, double m03, double m13, double m23, double m33) {
		setMatrix(m00, m10, m20, m30, m01, m11, m21, m31, m02, m12, m22, m32, m03, m13, m23, m33);
	}
	
	public Matrix4x4(double i) {
		m00 = i;
		m11 = i;
		m22 = i;
		m33 = i;
	}
	
	public Matrix4x4(Matrix4x4 mat) {
		m00 = mat.m00;
		m10 = mat.m10;
		m20 = mat.m20;
		m30 = mat.m30;
		m01 = mat.m01;
		m11 = mat.m11;
		m21 = mat.m21;
		m31 = mat.m31;
		m02 = mat.m02;
		m12 = mat.m12;
		m22 = mat.m22;
		m32 = mat.m32;
		m03 = mat.m03;
		m13 = mat.m13;
		m23 = mat.m23;
		m33 = mat.m33;
	}
	
	public void setMatrix(double m00, double m10, double m20, double m30, double m01, double m11, double m21, double m31, double m02, double m12, double m22, double m32, double m03, double m13, double m23, double m33) {
		this.m00 = m00;
		this.m10 = m10;
		this.m20 = m20;
		this.m30 = m30;
		
		this.m01 = m01;
		this.m11 = m11;
		this.m21 = m21;
		this.m31 = m31;
		
		this.m02 = m02;
		this.m12 = m12;
		this.m22 = m22;
		this.m32 = m32;
		
		this.m03 = m03;
		this.m13 = m13;
		this.m23 = m23;
		this.m33 = m33;
	}
	
	public void setMatrix(Matrix4x4 mat) {
		setMatrix(mat.m00, mat.m10, mat.m20, mat.m30, mat.m01, mat.m11, mat.m21, mat.m31, mat.m02, mat.m12, mat.m22, mat.m32, mat.m03, mat.m13, mat.m23, mat.m33);
	}
	
	public Matrix4x4 mul(Matrix4x4 m) {
		Matrix4x4 mat = new Matrix4x4(m00*m.m00+m10*m.m01+m20*m.m02+m30*m.m03, m00*m.m10+m10*m.m11+m20*m.m12+m30*m.m13, m00*m.m20+m10*m.m21+m20*m.m22+m30*m.m23, m00*m.m30+m10*m.m31+m20*m.m32+m30*m.m33,
									  m01*m.m00+m11*m.m01+m21*m.m02+m31*m.m03, m01*m.m10+m11*m.m11+m21*m.m12+m31*m.m13, m01*m.m20+m11*m.m21+m21*m.m22+m31*m.m23, m01*m.m30+m11*m.m31+m21*m.m32+m31*m.m33,
									  m02*m.m00+m12*m.m01+m22*m.m02+m32*m.m03, m02*m.m10+m12*m.m11+m22*m.m12+m32*m.m13, m02*m.m20+m12*m.m21+m22*m.m22+m32*m.m23, m02*m.m30+m12*m.m31+m22*m.m32+m32*m.m33,
									  m03*m.m00+m13*m.m01+m23*m.m02+m33*m.m03, m03*m.m10+m13*m.m11+m23*m.m12+m33*m.m13, m03*m.m20+m13*m.m21+m23*m.m22+m33*m.m23, m03*m.m30+m13*m.m31+m23*m.m32+m33*m.m33);
		
		return mat;
	}
	
	public void mulEqual(Matrix4x4 m) {
		double nm00 = m00*m.m00 + m10*m.m01 + m20*m.m02 + m30*m.m03;
		double nm10 = m00*m.m10+m10*m.m11+m20*m.m12+m30*m.m13;
		double nm20 = m00*m.m20+m10*m.m21+m20*m.m22+m30*m.m23;
		double nm30 = m00*m.m30+m10*m.m31+m20*m.m32+m30*m.m33;
		
		double nm01 = m01*m.m00+m11*m.m01+m21*m.m02+m31*m.m03;
		double nm11 = m01*m.m10+m11*m.m11+m21*m.m12+m31*m.m13;
		double nm21 = m01*m.m20+m11*m.m21+m21*m.m22+m31*m.m23;
		double nm31 = m01*m.m30+m11*m.m31+m21*m.m32+m31*m.m33;
		
		double nm02 = m02*m.m00+m12*m.m01+m22*m.m02+m32*m.m03;
		double nm12 = m02*m.m10+m12*m.m11+m22*m.m12+m32*m.m13;
		double nm22 = m02*m.m20+m12*m.m21+m22*m.m22+m32*m.m23;
		double nm32 = m02*m.m30+m12*m.m31+m22*m.m32+m32*m.m33;
		
		double nm03 = m03*m.m00+m13*m.m01+m23*m.m02+m33*m.m03;
		double nm13 = m03*m.m10+m13*m.m11+m23*m.m12+m33*m.m13;
		double nm23 = m03*m.m20+m13*m.m21+m23*m.m22+m33*m.m23;
		double nm33 = m03*m.m30+m13*m.m31+m23*m.m32+m33*m.m33;

		m00 = nm00;
		m10 = nm10;
		m20 = nm20;
		m30 = nm30;

		m01 = nm01;
		m11 = nm11;
		m21 = nm21;
		m31 = nm31;

		m02 = nm02;
		m12 = nm12;
		m22 = nm22;
		m32 = nm32;

		m03 = nm03;
		m13 = nm13;
		m23 = nm23;
		m33 = nm33;
	}
	
	public Matrix4x4 scaleMatrix(double x, double y, double z) {
		Matrix4x4 scale = new Matrix4x4(1);
		
		scale.m00 = x;
		scale.m11 = y;
		scale.m22 = z;
		scale.m33 = 1;
		
		setMatrix(mul(scale));
		
		return this;
	}
	
	public Matrix4x4 translateMatrix(double x, double y, double z) {
		Matrix4x4 translate = new Matrix4x4(1);
		
		translate.m30 = x;
		translate.m31 = y;
		translate.m32 = z;
		translate.m00 = 1;
		translate.m11 = 1;
		translate.m22 = 1;
		translate.m33 = 1;
		
		setMatrix(mul(translate));
		
		return this;
	}
	
	public Matrix4x4 rotateMatrix(double x, double y, double z) {
		x = Math.toRadians(x);
		y = Math.toRadians(y);
		z = Math.toRadians(z);
		
		double sinX = Math.sin(x);
		double sinY = Math.sin(y);
		double sinZ = Math.sin(z);
		double cosX = Math.cos(x);
		double cosY = Math.cos(y);
		double cosZ = Math.cos(z);
		
		Matrix4x4 rotX = new Matrix4x4(1, 0,    0,     0,
									   0, cosX, -sinX, 0,
									   0, sinX,	cosX,  0,
									   0, 0,    0,     1);
																	 
		Matrix4x4 rotY = new Matrix4x4(cosY,	0, sinY, 0,
									   0,       1, 0,    0,
									   -sinY,   0, cosY, 0,
									   0,       0, 0,    1);
																	 
		Matrix4x4 rotZ = new Matrix4x4(cosZ, -sinZ, 0, 0,
									   sinZ, cosZ,  0, 0,
									   0,    0,	    1, 0,
									   0,    0,	    0, 1);
		
		setMatrix(rotY);
		mulEqual(rotX);
		mulEqual(rotZ);
		
		return this;
	}
	
	public Matrix4x4 scaleMatrix(Vector3 scale) {
		return scaleMatrix(scale.x, scale.y, scale.z);
	}
	
	public Matrix4x4 translateMatrix(Vector3 position) {
		return translateMatrix(position.x, position.y, position.z);
	}
	
	public Matrix4x4 rotateMatrix(Vector3 rotation) {
		return rotateMatrix(rotation.x, rotation.y, rotation.z);
	}
	
	public String toString() {
		return "| " + m00 + " " + m10 + " " + m20 + " " + m30 + " |" + "\n" +
			   "| " + m01 + " " + m11 + " " + m21 + " " + m31 + " |" + "\n" +
			   "| " + m02 + " " + m12 + " " + m22 + " " + m32 + " |" + "\n" +
			   "| " + m03 + " " + m13 + " " + m23 + " " + m33 + " |";
	}
}