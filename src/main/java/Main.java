import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) {
        Map<String, String> map = new MyHashMap();
//        String a = map.put("1","1");
//        String b = map.put("1","2");
//
//        System.out.println(b);
        System.out.println(map.size());
        System.out.println(map.containsKey("1"));
    }
}
