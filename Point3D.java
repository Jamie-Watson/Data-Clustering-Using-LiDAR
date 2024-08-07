/**
 * 
 * @author Jamie Watson
 * 
 * 		This class creates points in 3D space and includes a distance method
 *
 */
public class Point3D {
	
	/**
	 * Instance variables
	 * -x: the x coordinate
	 * -y: the y coordinate
	 * -z: the z coordinate
	 * -clusterLabel: int value that stores the cluster the point is in
	 */
	private double x;
	private double y;
	private double z;
	public int clusterLabel;
	
	/**
	 * 
	 * Constructor for the Point3D class
	 * 
	 * @param xCoord is the x coordinate
	 * @param yCoord is the y coordinate
	 * @param zCoord is the z coordinate
	 */
	public Point3D(double xCoord, double yCoord, double zCoord) {
		this.x= xCoord;
		this.y=yCoord;
		this.z=zCoord;
		this.clusterLabel=-1;
	}
	
	/**
	 * Getter for the x axis coordinate
	 * @return the x coordinate
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Getter for the y axis coordinate
	 * @return the y coordinate
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Getter for the z axis coordinate
	 * @return the z coordinate
	 */
	public double getZ() {
		return this.z;
	}
	
	/**
	 * Method that calculates the distance between two points
	 * 
	 * @param pt is the point that the point of this class is being compared with
	 * @return The distance between the two points
	 */
	public double distance (Point3D pt) {
		double newX=this.x-pt.getX();
		double newY=this.y-pt.getY();
		double newZ=this.z-pt.getZ();
		
		double dist=Math.pow(((Math.pow(newX, 2))+(Math.pow(newY, 2))+(Math.pow(newZ, 2))),0.5);
		return dist;
		
	}
	
	
}
