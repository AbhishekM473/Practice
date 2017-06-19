#include <iostream>
#include <bits/stdc++.h>
using namespace std;

//For graph to contain Eulerian path and cycle, there should be 2 or 0 odd degree vertices resp.

class Graph{
    
    public:
    
    int V, odd = 0;
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
    
    void checkEulerian(){
        for(int i=0; i<adjList.size(); i++){
            if(adjList[i].size() % 2 == 1)
                odd++;
        }
    }        
    
};

void test(Graph g){
    g.checkEulerian();
    switch(g.odd){
        case 0:
            cout<<"Graph has Eulerian cycle"<<endl;;
            break;

        case 2:
            cout<<"Graph has Eulerian path"<<endl;
            break;

        default:
            cout<<"Graph is not Eulerian"<<endl;
    }                
}

int main() {
    // Let us create and test graphs shown in above figures
    Graph g1(5);
    g1.addEdge(1, 0);
    g1.addEdge(0, 2);
    g1.addEdge(2, 1);
    g1.addEdge(0, 3);
    g1.addEdge(3, 4);
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
    g3.addEdge(3, 4);
    g3.addEdge(1, 3);
    test(g3);
 
    // Let us create a graph with 3 vertices
    // connected in the form of cycle
    Graph g4(3);
    g4.addEdge(0, 1);
    g4.addEdge(1, 2);
    g4.addEdge(2, 0);
    test(g4);
 
    // Let us create a graph with all veritces
    // with zero degree
    Graph g5(3);
    test(g5);
    return 0;
}
