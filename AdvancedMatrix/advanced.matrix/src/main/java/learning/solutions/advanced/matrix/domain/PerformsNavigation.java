package learning.solutions.advanced.matrix.domain;

import java.util.List;

public interface PerformsNavigation {
	List<NavCell> navigate(NavCell start, NavCell end);
}
