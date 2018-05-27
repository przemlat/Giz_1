import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pruffer {
    public static void main(String[] args) throws IOException {
        Graph graph = new SingleGraph("Pruffer graph");

        FileReader fileReader = new FileReader("pruffer.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String text = "";
        String line = bufferedReader.readLine();
        int lineNumber = 0;

        while (line != null) {
            lineNumber++;
            if (lineNumber == 2) {
                text += line;
                lineNumber = 0;
            }
            line = bufferedReader.readLine();
        }

        String[] numbersFromFile = text.split("\\s+");

        Node A[] = new Node[numbersFromFile.length + 3];

        for (int i = 0; i < numbersFromFile.length + 3; i++) {
            A[i] = graph.addNode(String.valueOf(i));
        }

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        ArrayList<Integer> prufferArr = new ArrayList<Integer>();

        for (int i = 1; i < numbersFromFile.length + 3; i++) {
            numbers.add(i);
        }

        for (int i = 0; i < numbersFromFile.length; i++) {
            prufferArr.add(Integer.parseInt(numbersFromFile[i]));
        }

        try {
            for (int j = 0; j < numbers.size(); j++) {
                if (numbers.get(j) != prufferArr.get(0)) {
                    for (int k = 0; k < prufferArr.size(); k++) {
                        if (prufferArr.get(k) == numbers.get(j)) {
                            k = prufferArr.size();
                        } else if (prufferArr.get(k) != numbers.get(0) && k == (prufferArr.size() - 1)) {
                            String nameOfEdge = String.valueOf(prufferArr.get(0)) + String.valueOf(numbers.get(j));
                            graph.addEdge(nameOfEdge, prufferArr.get(0), numbers.get(j));
                            k = prufferArr.size();
                            numbers.remove(j);
                            prufferArr.remove(0);
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

        graph.removeNode(A[0]);
        graph.display();
    }
}