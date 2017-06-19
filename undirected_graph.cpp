#include <iostream>
#include <bits/stdc++.h>
using namespace std;


class Graph{
    
    public:
    
    int V;
    vector<vector<int>> adjList;
    
    Graph(int nV){
        V = nV;
        adjList.resize(V, vector<int>(0,0));
    }
    
    void addEdge(int src, int dest){
        //Undirected
        adjList[src].push_back(dest);
        adjList[dest].push_back(src);
    }
    
    void printGraph(){
        for(int i=0; i<adjList.size(); i++){
            for(int j=0; j<adjList[i].size(); j++){
                cout<<adjList[i][j]<<" ";
            }
            cout<<endl;
        }
        cout<<"--------------"<<endl;
    }
    
};

int main() {
    // Let us create and test graphs shown in above figures
    Graph g1(5);
    g1.addEdge(1, 0);
    g1.addEdge(0, 2);
    g1.addEdge(2, 1);
    g1.addEdge(0, 3);
    g1.addEdge(3, 4);
    g1.printGraph();
 
    Graph g2(5);
    g2.addEdge(1, 0);
    g2.addEdge(0, 2);
    g2.addEdge(2, 1);
    g2.addEdge(0, 3);
    g2.addEdge(3, 4);
    g2.addEdge(4, 0);
    g2.printGraph();
 
    Graph g3(5);
    g3.addEdge(1, 0);
    g3.addEdge(0, 2);
    g3.addEdge(2, 1);
    g3.addEdge(0, 3);
    g3.addEdge(3, 4);
    g3.addEdge(1, 3);
    g3.printGraph();
 
    // Let us create a graph with 3 vertices
    // connected in the form of cycle
    Graph g4(3);
    g4.addEdge(0, 1);
    g4.addEdge(1, 2);
    g4.addEdge(2, 0);
    g4.printGraph();
     
    return 0;
}
