import java.util.*;
import java.awt.Color;

public class ImageSegmenter {

	public static Color[][] segment(Color[][] rgbArray, double granularity) {
		// COMPLETE ME!
		Pixel[][] pix = new Pixel[rgbArray.length][rgbArray[0].length];
		for(int i = 0; i < rgbArray.length; i++) {
			for(int j = 0; j < rgbArray[0].length; j++) {
				pix[i][j] = new Pixel(i, j, rgbArray[i][j]);
			}
		}

		TreeSet <Edge> collect = new TreeSet<Edge>();
		for(int i = 0; i < rgbArray.length; i++) {
			for(int j = 0; j < rgbArray[0].length; j++) {
				if(i+1 < rgbArray.length) {
					collect.add(new Edge(pix[i][j], pix[i+1][j]));
				}
				if(j-1 >= 0) {
					collect.add(new Edge(pix[i][j], pix[i][j-1]));
				}
				if(i+1<rgbArray.length && j+1 < rgbArray[0].length ) {
					collect.add(new Edge(pix[i][j], pix[i+1][j+1]));
				}
				if(i-1 >= 0 && j+1 > rgbArray[0].length ) {
					collect.add(new Edge(pix[i][j], pix[i-1][j+1]));
				}

			}
		}
		DisjointSetForest forest = new DisjointSetForest(pix);
		Edge edgy;
		while(!collect.isEmpty()) {
			edgy = collect.pollFirst();
			Pixel first = edgy.getFirstPixel();
			Pixel second = edgy.getSecondPixel();
			int row1 = first.getRow();
			int col1 = first.getCol();
			int row2 = second.getRow();
			int col2 = second.getCol();
			if(forest.find(first) != forest.find(second)) {
				if(edgy.getWeight() < Double.min(forest.findInternalDistance(first)+Math.abs(granularity/forest.size(row1,
						col1)), forest.findInternalDistance(second)+Math.abs(granularity/forest.size(row2,col2)) )) {
					forest.union(first, second);
					forest.setInternalDistance(first, edgy.getWeight());
					forest.setInternalDistance(second, edgy.getWeight());
				}
			}

		}

		for(int i = 0; i < rgbArray.length; i++) {
			for(int j = 0; j < rgbArray[0].length; j++) {
				rgbArray[i][j] = forest.colorMap(pix[i][j]);
			}
		}


		// placeholder to ensure compilation -- replace this when you're done!
		return rgbArray; 
	}
}