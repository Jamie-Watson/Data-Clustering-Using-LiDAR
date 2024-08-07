/**
 * 
 * @author Jamie Watson
 * 
 * 		This class implements the nearest neighbor methods
 *
 */

/**
 * Imports Needed:
 * Includes import for the List interface
 * 
 */
import java.util.ArrayList;
import java.util.List;

public class NearestNeighbors {
	
	/*
	 * Instance variable that contains the list of points
	 */
	List<Point3D> pts;
	
	/**
	 * Constructor for the NearestNeighbors class
	 * @param pts is the list of points
	 */
	public NearestNeighbors(List<Point3D> pts) {
		this.pts=pts;
	}
	
	/**
	 * Method that finds the nearest neighbors of a 3D point
	 * 
	 * @param eps is the minimum distance to be considered close
	 * @param P is the point to be checked
	 * @return The list containing the neighbors of point P
	 */
	public List<Point3D> rangeQuery(double eps, Point3D P){
		
		List<Point3D> neighbors=new ArrayList <Point3D>();
		
		
		for(Point3D currentPoint: pts) {
			if (P.distance(currentPoint)<=eps) {
				neighbors.add(currentPoint);
			}
		}
		
		return neighbors;
		
	
	}
}
