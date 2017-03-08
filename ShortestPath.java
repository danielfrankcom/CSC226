
/* ShortestPath.java
   CSC 226 - Spring 2017

   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
    java ShortestPath

   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
    java ShortestPath file.txt
   where file.txt is replaced by the name of the text file.

   The input consists of a series of graphs in the following format:

    <number of vertices>
    <adjacency matrix row 1>
    ...
    <adjacency matrix row n>

   Entry A[i][j] of the adjacency matrix gives the weight of the edge from
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].

   An input file can contain an unlimited number of graphs; each will be
   processed separately.


   B. Bird - 08/02/2014
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.PriorityQueue;


//Do not change the name of the ShortestPath class
public class ShortestPath{

    public static int numVerts;
    public static Node[] nodes;


    /* ShortestPath(G)
        Given an adjacency matrix for graph G, calculates and stores the shortest paths to all the
                vertces from the source vertex.

        If G[i][j] == 0, there is no edge between vertex i and vertex j
        If G[i][j] > 0, there is an edge between vertices i and j, and the
        value of G[i][j] gives the weight of the edge.
        No entries of G will be negative.
    */
    static void ShortestPath(int[][] G, int source){

        numVerts = G.length;

        nodes = new Node[numVerts];
        Node sourceNode = new Node(source, 0);
        nodes[source] = sourceNode;

        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(sourceNode);

        for(int tempVert = 0; tempVert < numVerts; tempVert++){
            if(tempVert != source){
                Node tempNode = new Node(tempVert, Integer.MAX_VALUE);
                nodes[tempVert] = tempNode;
            }
        }

        while(!q.isEmpty()){
            Node tempVert = q.poll();
            for(int neighborId = 0; neighborId < numVerts; neighborId++){
                int weight = G[tempVert.getId()][neighborId];
                if(weight != 0){
                    Node tempNeighbor = nodes[neighborId];
                    int alternate = tempVert.getDistance() + weight;
                    if(alternate < tempNeighbor.getDistance()){
                        tempNeighbor.setDistance(alternate);
                        tempNeighbor.setPrevious(tempVert);
                        q.add(tempNeighbor);
                    }
                }
            }
        }

    }

    public static void PrintPaths(int source){
        for(int i = 0; i < numVerts; i++){
            Stack<Integer> pathList = new Stack<>();
            Node curr = nodes[i];
            while(curr.getPrevious() != null){
                pathList.push(curr.getId());
                curr = curr.getPrevious();
            }
            System.out.print("The path from " + source + " to " + i + " is: " + source);
            while(!pathList.isEmpty()){
                System.out.print("-->" + pathList.pop());
            }
            System.out.println(" and the total distance is: " + nodes[i].getDistance());
        }
    }

    /* main()
       Contains code to test the ShortestPath function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args) throws FileNotFoundException{
        Scanner s;
        if (args.length > 0){
            try{
                s = new Scanner(new File(args[0]));
            } catch(java.io.FileNotFoundException e){
                System.out.printf("Unable to open %s\n",args[0]);
                return;
            }
            System.out.printf("Reading input values from %s.\n",args[0]);
        }else{
            s = new Scanner(System.in);
            System.out.printf("Reading input values from stdin.\n");
        }

        int graphNum = 0;
        double totalTimeSeconds = 0;

        //Read graphs until EOF is encountered (or an error occurs)
        while(true){
            graphNum++;
            if(graphNum != 1 && !s.hasNextInt())
                break;
            System.out.printf("Reading graph %d\n",graphNum);
            int n = s.nextInt();
            int[][] G = new int[n][n];
            int valuesRead = 0;
            for (int i = 0; i < n && s.hasNextInt(); i++){
                for (int j = 0; j < n && s.hasNextInt(); j++){
                    G[i][j] = s.nextInt();
                    valuesRead++;
                }
            }
            if (valuesRead < n*n){
                System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
                break;
            }
            long startTime = System.currentTimeMillis();

            ShortestPath(G, 0);
            PrintPaths(0);
            long endTime = System.currentTimeMillis();
            totalTimeSeconds += (endTime-startTime)/1000.0;

            //System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
        }
        graphNum--;
        System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}

class Node implements Comparable<Node>{
    private int id;
    private int distance;
    private Node previous;

    public Node(int id, int dist){
        this.id = id;
        this.distance = dist;
        this.previous = null;
    }

    public int getId(){
        return id;
    }

    public int getDistance(){
        return distance;
    }

    public void setDistance(int dist){
        distance = dist;
    }

    public Node getPrevious(){
        return previous;
    }

    public void setPrevious(Node prev){
        previous = prev;
    }

    @Override
    public int compareTo(Node t) {
        if(distance == t.distance){
            return 0;
        }else if(distance < t.distance){
            return -1;
        }else{
            return 1;
        }
    }
}
