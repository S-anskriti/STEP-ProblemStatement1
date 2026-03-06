```java
import java.util.*;

public class ProblemStatement1 {

    static int L1_LIMIT = 3;
    static int L2_LIMIT = 5;

    static LinkedHashMap<String,String> L1 = new LinkedHashMap<>(16,0.75f,true){
        protected boolean removeEldestEntry(Map.Entry<String,String> e){
            return size()>L1_LIMIT;
        }
    };

    static LinkedHashMap<String,String> L2 = new LinkedHashMap<>(16,0.75f,true){
        protected boolean removeEldestEntry(Map.Entry<String,String> e){
            return size()>L2_LIMIT;
        }
    };

    static HashMap<String,String> L3 = new HashMap<>();
    static HashMap<String,Integer> access = new HashMap<>();

    static int l1Hit=0,l2Hit=0,l3Hit=0;

    public static void main(String[] args){

        L3.put("video_123","VideoData123");
        L3.put("video_456","VideoData456");
        L3.put("video_999","VideoData999");

        getVideo("video_123");
        getVideo("video_123");
        getVideo("video_999");

        getStatistics();
    }

    static String getVideo(String id){

        if(L1.containsKey(id)){
            l1Hit++;
            System.out.println("L1 Cache HIT");
            return L1.get(id);
        }

        if(L2.containsKey(id)){
            l2Hit++;
            System.out.println("L2 Cache HIT");
            String data=L2.get(id);
            L1.put(id,data);
            return data;
        }

        if(L3.containsKey(id)){
            l3Hit++;
            System.out.println("L3 Database HIT");
            String data=L3.get(id);
            L2.put(id,data);
            access.put(id,access.getOrDefault(id,0)+1);
            return data;
        }

        return "Not Found";
    }

    static void getStatistics(){

        int total=l1Hit+l2Hit+l3Hit;

        double r1=(l1Hit*100.0)/total;
        double r2=(l2Hit*100.0)/total;
        double r3=(l3Hit*100.0)/total;

        System.out.println("L1 Hit Rate "+r1+"%");
        System.out.println("L2 Hit Rate "+r2+"%");
        System.out.println("L3 Hit Rate "+r3+"%");
    }
}
