import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Pruffer {
    public static void main(String[] args) throws IOException {
        Graph graph = new SingleGraph("Pruffer");

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

        String[] nodeNumbersFromFile = text.split("\\s+");

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        for (int i = 1; i < nodeNumbersFromFile.length + 3; i++) {
            numbers.add(i);
        }

        ArrayList<Integer> nodeNumbers = new ArrayList<Integer>();
        for (String aNodeNumbersFromFile : nodeNumbersFromFile) {
            nodeNumbers.add(Integer.parseInt(aNodeNumbersFromFile));
        }

        try {
            for (int j = 0; j < numbers.size(); j++) {
                if (numbers.get(j) != nodeNumbers.get(0)) {
                    for (int k = 0; k < nodeNumbers.size(); k++) {
                        if (nodeNumbers.get(k) == numbers.get(j)) {
                            k = nodeNumbers.size();
                        } else if (nodeNumbers.get(k) != numbers.get(0) && k == (nodeNumbers.size() - 1)) {
                            String nameOfEdge = String.valueOf(nodeNumbers.get(0)) + String.valueOf(numbers.get(j));
                            if (graph.getNode(String.valueOf(nodeNumbers.get(0))) == null) {
                                graph.addNode(String.valueOf(nodeNumbers.get(0)));
                            }
                            if (graph.getNode(String.valueOf(numbers.get(j))) == null) {
                                graph.addNode(String.valueOf(numbers.get(j)));
                            }
                            graph.addEdge(nameOfEdge, String.valueOf(nodeNumbers.get(0)), String.valueOf(numbers.get(j)));
                            k = nodeNumbers.size();
                            numbers.remove(j);
                            nodeNumbers.remove(0);
                            j = -1;

                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            if (graph.getNode(String.valueOf(numbers.get(numbers.size() - 2))) == null) {
                graph.addNode(String.valueOf(numbers.get(numbers.size() - 2)));
            }
            if (graph.getNode(String.valueOf(numbers.get(numbers.size() - 1))) == null) {
                graph.addNode(String.valueOf(numbers.get(numbers.size() - 1)));
            }
            String nameOfEdge = String.valueOf(numbers.get(numbers.size() - 2) + String.valueOf(numbers.get(numbers.size() - 1)));
            graph.addEdge(nameOfEdge, String.valueOf(numbers.get(numbers.size() - 2)), String.valueOf(numbers.get(numbers.size() - 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
        graph.display();
    }
}