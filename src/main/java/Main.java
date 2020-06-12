import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        for (int i = 0 ; i < 1000; i++){
            map.put(String.valueOf(i),String.valueOf(i));
        }
        map.put("463","463");
        System.out.println(map.get("1"));
        System.out.println(map.get("200"));
        System.out.println(map.get("2"));
        System.out.println(map.containsKey("1"));
        System.out.println(map.capacity());
        System.out.println(map.size());
    }
}
