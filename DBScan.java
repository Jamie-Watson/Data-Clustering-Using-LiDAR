/**
 * 
 * @author Jamie Watson
 * 		This class Implements the DBScan algorithm using java stacks.
 * 		This class also contains a main which allows reading and writing from and to csv files.
 *
 */

/**
 * Imports Needed:
 * Includes imports for file IO, Lists, and Stacks
 * 
 */

 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.OutputStreamWriter;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import java.util.Stack;
 
 public class DBScan {
	 
	 /**
	  * Instance variables
	  * -eps: stores the minimum distance for points to be considered neighbors
	  * -minPts: stores the minimum points in a region for a point to be considered in the dense region
	  * -pts: stores list of points
	  * -clusterCounter: stores total number of different clusters
	  * -RBG: stores RBG values for each cluster
	  * -clusterSizes: stores cluster sizes by cluster number index (including noise)
	  * -sortedClusterSizes: stores cluster sizes from smallest to largest (not including noise)
	  */
	 
	 double eps;
	 double minPts;
	 List<Point3D> pts;
	 int clusterCounter;
	 double[][]RGB;
	 int[]clusterSizes;
	 int[]sortedClusterSizes;
	 
	 
	 /**
	  * Constructor for DBScan
	  * 
	  * @param points is the list of points given in (x,y,z) format
	  */
	 public DBScan(List<Point3D> points) {
		 this.pts=points;
	 }
	 
	 /**
	  * Setter for the eps parameter
	  * 
	  * @param eps is the minimum distance parameter
	  */
	 public void setEps(double eps) {
		 this.eps=eps;
	 }
	 
	 /**
	  * Setter for the minPts parameter
	  * 
	  * @param minPts is the minimum points in a density region
	  */
	 public void setMinPts(int minPts) {
		 this.minPts=minPts;
	 }
	 
	 /**
	  * Method that executes the DBScan algorithm
	  * 
	  */
	 public void findClusters() {
		 
		 //set initial count to zero
		 clusterCounter=0;
		 
		 for(Point3D currentPoint: pts) {
			 
			 //-1 will represent the unassigned points
			 if(currentPoint.clusterLabel!=-1) {
				 continue;
			 }
			 
			 
			 NearestNeighbors neighbors=new NearestNeighbors(pts);
			 List<Point3D> N=neighbors.rangeQuery(eps, currentPoint);
				 
			 if(N.size()<minPts) {		
				 //0 will represent Noise
				 currentPoint.clusterLabel=0;
				 continue;
			 }
			 
			 clusterCounter++;
			 
			 currentPoint.clusterLabel=clusterCounter;
			 Stack<Point3D> S = new Stack<Point3D>();
			 S.addAll(N);
			 for(Point3D neighbor: N) {
				 S.push(neighbor);
			 }
			 
			 while(!S.isEmpty()) {
				 Point3D Q=S.pop();
				 if(Q.clusterLabel==0) {
					 Q.clusterLabel=clusterCounter;
				 }
				 
				 if(Q.clusterLabel!=-1) {
					 continue;
				 }
				 
				 Q.clusterLabel=clusterCounter;
				 NearestNeighbors QNeighbors=new NearestNeighbors(pts);
				 List<Point3D> QN=QNeighbors.rangeQuery(eps, Q);
				 
				 if(QN.size()>=minPts) {
					 for(Point3D QNeighbor:QN) {
						 S.push(QNeighbor);
					 }
				 }
				 
			 }
				 
		 }
				 
		 
	 }
	 
	 /**
	  * Getter for the number of clusters instance variable
	  * 
	  * @return The number of clusters in the points set
	  */
	 public int getNumberOfClusters() {
		 return clusterCounter;
	 }
	 
	 /**
	  * Getter for the list of points instance varibles
	  * 
	  * @return The list of points
	  */
	 public List<Point3D> getPoints(){
		 return this.pts;
	 }
	 
	 /**
	  * 
	  * Method that reads the input csv file
	  * 
	  * @param filename is the name of the file
	  * @return The list of points gained from the csv file
	  * @throws IOException
	  */
	 public static List<Point3D> read(String filename) throws IOException{
		 
		 //list of points to be returned
		 List<Point3D> pts=new ArrayList<Point3D>();
		 
		 //buffered reader to read the file
		 BufferedReader in;
		 
		 
		 try {
			 in = new BufferedReader(new InputStreamReader(  new FileInputStream(filename) ) );
			 String currentLine;
			 
			 //reads the first line to ignore the x,y,z line
			 in.readLine();
			 
			 //for each line in the file:
			 while ((currentLine = in.readLine()) != null) {
 
				   //splits based on the comma
				   String[] pointData = currentLine.split(",");
				   double xData=Double.parseDouble(pointData[0]);
				   double yData=Double.parseDouble(pointData[1]);
				   double zData=Double.parseDouble(pointData[2]);
				   pts.add(new Point3D(xData,yData,zData));
			 }
				   
		 } 
		 
		 //print statement for when a file is not found
		 catch (FileNotFoundException e) {
			 System.out.println("File not found in directory");
		 }
		 return pts;
		 
		 
			   
	 }
	 
	 /**
	  * 
	  * Method that saves the result to another csv file
	  * 
	  * @param filename is the file name
	  * @throws IOException
	  */
	 public void save(String filename) throws IOException{
		 
		 //using a buffered writer to output
		 BufferedWriter out;
		 
		 //generates the RGB if not already generated
		 if (RGB==null) {
			 RGB=generateColour(clusterCounter);
		 }
		 
		 //if there exists a file by that name
		 try {
			 out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
			 
			 //using a string builder for time efficiency
			 StringBuilder currentLine = new StringBuilder();
			 
			 //writes the header for the file
			 out.write("x,y,z,C,R,G,B");
			 out.newLine();
			 
			 //writes the points
			 for (Point3D pt : this.pts) {
				 currentLine.setLength(0);  
				 currentLine.append(pt.getX()).append(",")
							.append(pt.getY()).append(",")
							.append(pt.getZ()).append(",")
							.append(pt.clusterLabel).append(",")
							.append(RGB[pt.clusterLabel][0]).append(",")
							.append(RGB[pt.clusterLabel][1]).append(",")
							.append(RGB[pt.clusterLabel][2]);
 
				 out.write(currentLine.toString());
				 out.newLine();
			 }
			 
			 out.flush();
			 
		 }
		 
		 //if a file needs to be created
		 catch (FileNotFoundException e) {
			 
			 //gets and creates the new file name
			 String currentDir = System.getProperty("user.dir");
			 String filePath = currentDir + File.separator + filename;
			 
			 File outputFile = new File(filePath);
			 
			 out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
			 
			 //using a string builder for time efficiency
			 StringBuilder currentLine = new StringBuilder();
			 
			 //writes the header for the file
			 out.write("x,y,z,C,R,G,B");
			 out.newLine();
			 
			 //writes the points
			 for (Point3D pt : this.pts) {
				 currentLine.setLength(0);  
				 currentLine.append(pt.getX()).append(",")
							.append(pt.getY()).append(",")
							.append(pt.getZ()).append(",")
							.append(pt.clusterLabel).append(",")
							.append(RGB[pt.clusterLabel][0]).append(",")
							.append(RGB[pt.clusterLabel][1]).append(",")
							.append(RGB[pt.clusterLabel][2]);
 
				 out.write(currentLine.toString());
				 out.newLine();
			 }
 
			 out.flush();
		 }
		 return;
		 
	 }
	 
	 /**
	  * Generates the RGB colours unique to each cluster
	  * 
	  * @param size is the number of different clusters
	  * @return The 2D array of RGB values respective to their cluster
	  */
	 public double[][] generateColour(int size){
		 double s=(double)size;
		 double[][] colours=new double[size+1][3];
		 for(int i=0;i<=size;i++) {
			 colours[i][0]=(i/s);
			 colours[i][1]=1-(i/s);
			 colours[i][2]=i%2;
			 
		 }
		 return colours;
	 }
	 
	 /**
	  * Method that assigns the cluster sizes array 
	  */
	 public void generateClusterSize() {
		 
		 if(clusterSizes==null) {
			 clusterSizes=new int[clusterCounter+1];
		 }
		 
		 for(int i=0; i<clusterCounter+1;i++) {
			 clusterSizes[i]=0;
		 }
		 
		 for (Point3D pt:pts) {
			 clusterSizes[pt.clusterLabel]++;
		 }
		 
	 }
	 
	 /**
	  * Method that generates the output file name given the input file name
	  * 
	  * @param in is the input file name
	  * @return the output file name
	  */
	 public String outputFileName(String in) {
		 String[] parts=in.split("\\.");
		 String out=parts[0];
		 out+="_clusters_"+eps+"_"+minPts+"_"+clusterCounter+".";
		 out+=parts[1];
		 return out;
	 }
	 
	 /**
	  * Method that prints the cluster size data
	  */
	 public void outputClusterData() {
		 
		 //if the sorted cluster size array is unassigned
		 if(sortedClusterSizes==null) {
			 
			 //removing the 0 index(noise points)
			 sortedClusterSizes=new int[clusterSizes.length-1];
			 
			 //created the sortedClusterSizes array
			 for(int i=0;i<sortedClusterSizes.length;i++) {
				 sortedClusterSizes[i]=clusterSizes[i+1];
			 }
			 Arrays.sort(sortedClusterSizes);
		 }
		 
		 //printing all cluster data
		 for (int i=sortedClusterSizes.length-1;i>=0;i--) {
			 for(int j=1;j<clusterSizes.length;j++) {
				 if (sortedClusterSizes[i]==clusterSizes[j]) {
					 System.out.println("Cluster Number(label): "+ j+"\nCluster Size: "+sortedClusterSizes[i]);
				 }
			 }
		 }
		 System.out.println("Noise Points(Cluster 0): "+clusterSizes[0]);
	 }
 
	 /**
	  * Main method
	  * 
	  * @param args is the input file name in the for java Main
	  * 
	  */
	 public static void main(String[] args) {
		 
		 //if input data is incorrect
		 if (args.length != 3) {
			 throw new IllegalArgumentException("Usage: java Main <input filename> <eps> <minPts>");
		 }
 
		 //getting variables from args
		 String filename = args[0];
		 double eps = Double.parseDouble(args[1]);
		 int minPts = Integer.parseInt(args[2]);
		 
		 
		 List <Point3D> points;
		 
		 //reading the file
		 System.out.println("Reading file.");
		 
		 try {
			 points= read(filename);
		 } catch (IOException e) {
			 
			 System.out.println("File name not found");
			 return;
		 }
		 
		 //creating the DBScan class
		 DBScan DB=new DBScan(points);
		 DB.setEps(eps);
		 DB.setMinPts(minPts);
		 
		 //finding clusters
		 System.out.println("Finding clusters.");
		 DB.findClusters();
		 
		 //writing to output file
		 System.out.println("Clusters found, writing to file.");
		 try {
			 DB.save(DB.outputFileName(filename));
		 } catch (IOException e) {
			 System.out.println("File name not found");
			 return;
		 }
		 
		 //generating cluster data
		 DB.generateClusterSize();
	 
		 //outputting cluster data
		 DB.outputClusterData();
		 
		 
		 
		 
	 }
 }
 