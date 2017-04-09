package learning.solutions.advanced.matrix.domain;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Properties;

import learning.solutions.advanced.matrix.utils.EnvironmentUtils;

public class MatrixArchitect extends Frame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4198825794676956718L;
	private Properties			matrixDefinition	= null;
	private List<MatrixElement>	matrix				= null;

	private void render(Graphics2D canvas) {
		for (MatrixElement mElement : matrix) {
			mElement.draw(canvas);
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D canvas = (Graphics2D) g;
		render(canvas);
	}

	public MatrixArchitect(List<MatrixElement> matrix, Properties matrixDefinition) {
		super("Matrix");
		this.matrixDefinition = matrixDefinition;
		this.matrix = matrix;

		int frameHeight = Integer.parseInt(this.matrixDefinition.getProperty(EnvironmentUtils.FRAME_HEIGHT_PROPERTY));
		int frameWidth = Integer.parseInt(this.matrixDefinition.getProperty(EnvironmentUtils.FRAME_WIDTH_PROPERTY));
		this.setSize(frameHeight, frameWidth);
		this.setVisible(true);
	}
}
