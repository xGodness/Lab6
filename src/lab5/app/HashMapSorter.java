package lab5.app;

import java.util.*;

public class HashMapSorter {

    public static final Comparator<Map.Entry<String, String>> comparator = new Comparator<Map.Entry<String, String>>() {

        @Override
        public int compare(Map.Entry<String, String> entry1, Map.Entry<String, String> entry2) {
            return entry1.getValue().compareTo(entry2.getValue());
        }
    };

    public static LinkedHashMap sortHashMap(HashMap<String, String> hashMap) {

        List<Map.Entry<String, String>> entryList = new LinkedList<>(hashMap.entrySet());

        entryList.sort(comparator);

        LinkedHashMap<String, String> sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entryList) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }

        return sortedHashMap;
    }



}
