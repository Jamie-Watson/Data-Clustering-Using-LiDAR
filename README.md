# Data Clustering Using LiDAR

Programming experiment clustering data using the DBScan algorithm. Backend with Java and visualizer with Python.

## Running

To install and run the project, follow these steps:

1. Click on the green "Code" button on the upper right-hand corner.
2. Download the ZIP file.
3. Extract the file.
4. Navigate to the directory where the folder is stored:
    ```sh
    cd file-directory
    ```
5. Either run the code using a preferred IDE or through the command line:

    **Compile Java files:**
    ```sh
    javac DBScan.java
    ```

    **Run the application:**
    ```sh
    java DBScan Point_Cloud_1.csv 1.2 10
    ```

    Finally, running your program must be done by specifying the input filename (this example uses `Point_Cloud_1.csv`), the parameters `eps` and `minPts`, exactly as follows: 
    ```sh
    java DBScan Point_Cloud_1.csv 1.2 10
    ```

    There are other input files contained in the directory including: `Point_Cloud_1.csv`, `Point_Cloud_2.csv`, and `Point_Cloud_3.csv`. Reference photos of the scene have also been provided.

6. Note that this project will create another output file of the clustered points in the form `filename_clusters_eps_minPts_nclusters.csv`. For example, with the input file `data.csv`, the output file could be:
`data_clusters_1.2_10_57.csv`

7. Use the Python visualizer:
    To view the clustered points, run the Python application titled `view_clusters.py`.

    **Run the application:**
    ```sh
    python view_clusters.py
    ```

    Note that you may need to alter the code to open the correct file (it will default to `Point_Cloud_1_clusters_1.2_10.0_29.csv`).
