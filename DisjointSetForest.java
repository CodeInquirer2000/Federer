import java.util.HashMap;
import java.util.Map;
import java.awt.Color;

public class DisjointSetForest {
	private Node [][] forest;
	int length;
	int width;
	Map<Node, Color> colorMap;
	ColorPicker temp ;
	private class Node {
		Pixel pixel;
		Node parent;
		double internalDistance;
		double rank;
		int size;

		private Node(Pixel pixNode) {
			pixel = pixNode;
			rank = 0;
			parent = null;
			size=1;

		}

	}

	DisjointSetForest(Pixel[][] rgbArray) {
		length = rgbArray.length;
		width = rgbArray[0].length;
		forest = new Node [length][width];
		colorMap = new HashMap<Node, Color>();
		temp = new ColorPicker();
		for (int i = 0; i < rgbArray.length; i++) {
			for (int j = 0; j < rgbArray[0].length; j++) {
				forest[i][j] = new Node(rgbArray[i][j]);
			}
		}
	}
	void union(Pixel pixel1, Pixel pixel2) {
		int row1 = find(pixel1).getRow();
		int col1 = find(pixel1).getCol();
		int row2 = find(pixel2).getRow();
		int col2 = find(pixel2).getCol();

		if (forest[row1][col1].rank == forest[row2][col2].rank) {
			forest[row1][col1].rank += 1;
			forest[row1][col1].size += forest[row2][col2].size;
			forest[row2][col2].parent = forest[row1][col1];
		}
		else if (forest[row1][col1].rank < forest[row2][col2].rank) {
			forest[row1][col1].parent = forest[row2][col2];
			forest[row2][col2].size += forest[row1][col1].size;
		}
		else if (forest[row1][col1].rank > forest[row2][col2].rank) {
			forest[row2][col2].parent = forest[row1][col1];
			forest[row1][col1].size += forest[row2][col2].size;
		}
	}
	Pixel find(Pixel finder) {
		int col = finder.getCol();
		int row = finder.getRow();
		Node holder = forest[row][col];
		while (holder.parent != null) {
			holder = holder.parent;
		}
		return holder.pixel;
	}
	double findInternalDistance(Pixel finder) {
		int col = finder.getCol();
		int row = finder.getRow();
		return forest[row][col].internalDistance;
	}
	void setInternalDistance(Pixel finder, double distance) {
		int col = finder.getCol();
		int row = finder.getRow();
		forest[row][col].internalDistance = distance;
	}
	int size(int row, int col) {
		Node holder = forest[row][col];
		while (holder.parent != null) {
			holder = holder.parent;
		}
		return holder.size;
	}
	Color colorMap(Pixel pxl) {
		int row = find(pxl).getRow();
		int col = find(pxl).getCol();
		if (colorMap.containsKey(forest[row][col])) {
			return colorMap.get(forest[row][col]);
		}
		Color tempColor = temp.nextColor();
		colorMap.put(forest[row][col], tempColor);
		return tempColor;
	}
}
