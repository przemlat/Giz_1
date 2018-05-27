import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Graph graph = new SingleGraph("Prufer graph");

        int[] prufer = {1, 3, 6, 11, 5, 5, 3, 6, 11};

        Node A[] = new Node[prufer.length + 3];

        for (int i = 0; i < prufer.length + 3; i++) {
            A[i] = graph.addNode(String.valueOf(i));
        }

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        ArrayList<Integer> pruferArr = new ArrayList<Integer>();

        for (int i = 1; i < prufer.length + 3; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < prufer.length; i++) {
            pruferArr.add(prufer[i]);
        }

        try {
            for (int j = 0; j < numbers.size(); j++) {
                if (numbers.get(j) != pruferArr.get(0)) {
                    for (int k = 0; k < pruferArr.size(); k++) {
                        if (pruferArr.get(k) == numbers.get(j)) {
                            k = pruferArr.size();
                        } else if (pruferArr.get(k) != numbers.get(0) && k == (pruferArr.size() - 1)) {
                            String nameOfEdge = String.valueOf(pruferArr.get(0)) + String.valueOf(numbers.get(j));
                            graph.addEdge(nameOfEdge, pruferArr.get(0), numbers.get(j));
                            k = pruferArr.size();
                            numbers.remove(j);
                            pruferArr.remove(0);
                            j = -1;

                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            String nameOfEdge = String.valueOf(numbers.get(numbers.size() - 2) + String.valueOf(numbers.get(numbers.size() - 1)));
            graph.addEdge(nameOfEdge, numbers.get(numbers.size() - 2), numbers.get(numbers.size() - 1));

        } catch (IdAlreadyInUseException e) {
            e.printStackTrace();
        } catch (EdgeRejectedException e) {
            e.printStackTrace();
        }

        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }

        graph.display();
    }
}