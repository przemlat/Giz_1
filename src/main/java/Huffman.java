import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Huffman {

    public static void main(String[] args) throws IOException {
        Graph graph = new SingleGraph("Huffman");

        String text = "";
        FileReader fileReader = new FileReader("huffman.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = bufferedReader.readLine();

        while (line != null) {
            text += line;
            line = bufferedReader.readLine();
        }

        HashMap<String, Double> map = findFrequency(text);

        getKeyOfMinValue(map);

        int mapSize = map.size();

        for (int i = 0; i <= mapSize - 2; i++) {
            String key1 = getKeyOfMinValue(map);
            Double value1 = map.get(key1);

            if (graph.getNode(String.valueOf(key1)) == null) {
                graph.addNode(String.valueOf(key1));
            }
            map.remove(key1);
            String key2 = getKeyOfMinValue(map);
            Double value2 = map.get(key2);

            if (graph.getNode(String.valueOf(key2)) == null) {
                graph.addNode(String.valueOf(key2));
            }

            map.remove(key2);
            map.put(String.valueOf(key1 + key2), value1 + value2);

            if (graph.getNode(String.valueOf(key1 + key2)) == null) {
                graph.addNode(String.valueOf(key1 + key2));
            }
            String nameOfEdge1 = String.valueOf(key1) + String.valueOf(key1 + key2);
            String nameOfEdge2 = String.valueOf(key2) + String.valueOf(key1 + key2);
            graph.addEdge(nameOfEdge1, String.valueOf(key1), String.valueOf(key1 + key2));
            graph.addEdge(nameOfEdge2, String.valueOf(key2), String.valueOf(key1 + key2));
        }

        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
        graph.display();
    }

    private static String getKeyOfMinValue(HashMap<String, Double> map) {
        Map.Entry<String, Double> min = null;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }
        }
        String key = min.getKey();
        return key;
    }

    private static HashMap<String, Double> findFrequency(String s) {
        HashMap<String, Double> map = new HashMap<String, Double>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Double val = map.get(c);
            if (val != null) {
                map.put(String.valueOf(c), val + 1);
            } else {
                map.put(String.valueOf(c), 1.0);
            }
        }

        Iterator<Map.Entry<String, Double>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Double> pair = iterator.next();
            Double newCount = (pair.getValue() == null) ? 0 : pair.getValue() / s.length();
            pair.setValue(newCount);
        }
        return map;
    }
}