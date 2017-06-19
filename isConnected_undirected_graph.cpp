#include <iostream>
#include <bits/stdc++.h>
using namespace std;

//This is wrong. Should use BFS/DFS. Will do later

class Graph{
    
    public:
    
    int V;
    vector<vector<int>> adjList;
    
    Graph(int nV){
        V = nV;
        adjList.resize(V, vector<int>(0,0));
    }
    
    void addEdge(int src, int dest){
        //undirected
        adjList[src].push_back(dest);
        adjList[dest].push_back(src);
        
    }        
    
};

bool isConnected(Graph g){
    for(int i=0; i<g.adjList.size(); i++){
        if(g.adjList[i].size() == 0)
            return false;
    }
    return true;        
}

void test(Graph g){
    if(isConnected(g))
        cout<<"Graph is connected"<<endl;
    else
        cout<<"Graph is disconnected"<<endl;
}

int main() {
    // Let us create and test graphs shown in above figures
    Graph g1(5);
    g1.addEdge(1, 0);
    g1.addEdge(0, 2);
    g1.addEdge(2, 1);
    g1.addEdge(0, 3);
    g1.addEdge(3, 2);
    test(g1);
    
 
    Graph g2(5);
    g2.addEdge(1, 0);
    g2.addEdge(0, 2);
    g2.addEdge(2, 1);
    g2.addEdge(0, 3);
    g2.addEdge(3, 4);
    g2.addEdge(4, 0);
    test(g2);
 
    Graph g3(5);
    g3.addEdge(1, 0);
    g3.addEdge(0, 2);
    g3.addEdge(2, 1);
    g3.addEdge(0, 3);
    g3.addEdge(3, 2);
    g3.addEdge(1, 3);
    test(g3);
 
    // Let us create a graph with 3 vertices
    // connected in the form of cycle
    Graph g4(3);
    g4.addEdge(0, 1);
    g4.addEdge(1, 2);
    g4.addEdge(2, 0);
    test(g4);
     
    return 0;
}
