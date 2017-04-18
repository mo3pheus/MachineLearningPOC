/**
 * 
 */
package learning.solutions.advanced.matrix;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import learning.solutions.advanced.matrix.domain.NavCell;

/**
 * @author sanketkorgaonkar
 *
 */
public class RefNavNodeTest {

	public static void main(String[] args) {
		Map<Integer, NavCell> graph = new HashMap<Integer, NavCell>();
		int x = 0;
		int y = 0;
		int cellWidth = 25;
		for (int i = 0; i < 10; i++) {
			Point temp = new Point(i * cellWidth, i * cellWidth);
			NavCell nCell = new NavCell(temp, i);
			graph.put(i, nCell);
		}

		NavCell grandParent = new NavCell(new Point(0, 0), -1);
		for (int i : graph.keySet()) {
			NavCell nCell = graph.get(i);
			nCell.setPaarent(grandParent);
		}

		for (int i : graph.keySet()) {
			System.out.println(graph.get(i).toString());
		}

	}

}
