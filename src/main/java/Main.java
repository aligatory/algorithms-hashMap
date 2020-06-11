import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
//        new HashMap().
        MyHashMap map = new MyHashMap();
        for (int i = 0 ; i < 17;i++){
            map.put(String.valueOf(i), String.valueOf(i));
        }
        System.out.println(map.size());
        System.out.println(map.get("12"));
        System.out.println(map.size());
        System.out.println(map.remove("12"));
        System.out.println(map.get("12"));
        System.out.println(map.size());
        System.out.println(map.capacity());

    }
}
