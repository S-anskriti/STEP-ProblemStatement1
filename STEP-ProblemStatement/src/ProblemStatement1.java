import java.util.*;

public class ProblemStatement1 {

    static HashMap<String,Integer> pageViews = new HashMap<>();
    static HashMap<String,Set<String>> uniqueVisitors = new HashMap<>();
    static HashMap<String,Integer> sources = new HashMap<>();

    public static void main(String[] args) {

        processEvent("/article/breaking-news","user_123","google");
        processEvent("/article/breaking-news","user_456","facebook");
        processEvent("/sports/championship","user_123","direct");
        processEvent("/sports/championship","user_789","google");

        getDashboard();
    }

    static void processEvent(String url,String userId,String source){

        pageViews.put(url,pageViews.getOrDefault(url,0)+1);

        if(!uniqueVisitors.containsKey(url)){
            uniqueVisitors.put(url,new HashSet<>());
        }
        uniqueVisitors.get(url).add(userId);

        sources.put(source,sources.getOrDefault(source,0)+1);
    }

    static void getDashboard(){

        List<Map.Entry<String,Integer>> list = new ArrayList<>(pageViews.entrySet());
        list.sort((a,b)->b.getValue()-a.getValue());

        System.out.println("Top Pages:");

        int count=0;
        for(Map.Entry<String,Integer> e:list){
            String url=e.getKey();
            int views=e.getValue();
            int unique=uniqueVisitors.get(url).size();

            count++;
            System.out.println(count+". "+url+" - "+views+" views ("+unique+" unique)");

            if(count==10) break;
        }

        System.out.println("Traffic Sources:");
        for(String s:sources.keySet()){
            System.out.println(s+" : "+sources.get(s));
        }
    }
}