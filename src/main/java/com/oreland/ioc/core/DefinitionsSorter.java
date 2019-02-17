package com.oreland.ioc.core;

import java.lang.reflect.Constructor;
import java.util.*;

public class DefinitionsSorter {

    public List<Edge> prepareEdges(List<Class> definitions) {
        List<Edge> edges = new ArrayList<>();

        for (Class aClass : definitions) {
            for (Constructor constructor : aClass.getDeclaredConstructors()) {
                for (Class parameterType : constructor.getParameterTypes()) {
                    edges.add(new Edge(definitions.indexOf(parameterType), definitions.indexOf(aClass)));
                }
            }
        }
        return edges;
    }
}

class Edge {
    int source, dest;

    public Edge(int source, int dest) {
        this.source = source;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", dest=" + dest +
                '}';
    }
}

class Graph {
    List<List<Integer>> adjList = null;

    List<Integer> indegree = null;

    Graph(List<Edge> edges, int N) {
        adjList = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            adjList.add(i, new ArrayList<>());
        }

        indegree = new ArrayList<>(Collections.nCopies(N, 0));

        for (int i = 0; i < edges.size(); i++) {
            int src = edges.get(i).source;
            int dest = edges.get(i).dest;

            adjList.get(src).add(dest);

            indegree.set(dest, indegree.get(dest) + 1);
        }
    }
}

class TopologicalSort {
    public static List<Integer> doTopologicalSort(Graph graph, int N) {
        List<Integer> L = new ArrayList<>();

        List<Integer> indegree = graph.indegree;

        Stack<Integer> S = new Stack<>();
        for (int i = 0; i < N; i++) {
            if (indegree.get(i) == 0) {
                S.add(i);
            }
        }

        while (!S.isEmpty()) {
            int n = S.pop();

            L.add(n);

            for (int m : graph.adjList.get(n)) {
                indegree.set(m, indegree.get(m) - 1);

                if (indegree.get(m) == 0) {
                    S.add(m);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (indegree.get(i) != 0) {
                return Collections.emptyList();
            }
        }

        return L;
    }

}
