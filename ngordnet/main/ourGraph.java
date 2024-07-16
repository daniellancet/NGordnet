package ngordnet.main;
import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.Set;
public class ourGraph {

    private class Node {
        int id;
        Set<Integer> children;

        public Node(int i){
            id = i;
            children = new HashSet<>();
        }


        public void addChild(int s) {
            if (id == s) {
                return;
            }
            children.add(s);
        }

    }
    Node [] nodes;

    public ourGraph(int size, String hyponymFile){
        nodes = new Node[size];
        for (int i = 0; i < size; i++) {
            nodes[i] = new Node(i);
        }

        In in = new In(hyponymFile);
        int[] children;
        while (in.hasNextLine()) {
            if (in.isEmpty()) {
                break;
            }
            String word = in.readLine();
            String[] temp2 = word.split(",");
            int parent = Integer.parseInt(temp2[0]);
            children = new int[temp2.length - 1];
            for (int i = 0; i < children.length; i++) {
                children[i] = Integer.parseInt(temp2[i + 1]);
            }

            for (int i : children) {
                this.addChild(parent, i);
            }
        }

    }


    public void addChild(int e, int v) {
        nodes[e].addChild(v);
    }

    public Set<Integer> childSet(int i) {
        return nodes[i].children;
    }


}
