package learning.solutions.advanced.matrix.domain;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Properties;

public abstract class MatrixElement {
	private Properties matrixConfig = null;

	public abstract void draw(Graphics2D canvas);

	public abstract Color getColor();

	public void setProperties(Properties matrixConfig) {
		this.matrixConfig = matrixConfig;
	}

	public Properties getMatrixConfig() {
		return matrixConfig;
	}
	
	public void setMatrixConfig(Properties matrixConfig){
		this.matrixConfig = matrixConfig;
	}
}
