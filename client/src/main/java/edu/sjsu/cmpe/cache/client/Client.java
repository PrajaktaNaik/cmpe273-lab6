package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
      //add all the servers
List<CacheServiceInterface> totalServers = new ArrayList<CacheServiceInterface>();
totalServers.add(new DistributedCacheService("http://localhost:3000"));
totalServers.add(new DistributedCacheService("http://localhost:3001"));
totalServers.add(new DistributedCacheService("http://localhost:3002"));

char[] input ={' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j'};

//put data in cache servers using consistent hashing
System.out.println("Add the data to Cahce!!");

for(int key=1;key<=input.length-1;key++)
{

int keyvalue = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), totalServers.size());
CacheServiceInterface serverToPutData = totalServers.get(keyvalue);
serverToPutData.put(key,Character.toString(input[key]));
System.out.println("PUT --> Key=" + key + " and Value="+ input[key] + " routed to Cache server at localhost://300"+ keyvalue);
}

System.out.println(" ");
 //GET data from Cache servers consistent hashing
System.out.println("GET data from Cache... ");

for(int key=1;key<input.length-1;key++){
 int keyvalue=Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), totalServers.size());
 CacheServiceInterface serverToGetData =totalServers.get(keyvalue);
 String value = serverToGetData.get(key);
System.out.println("GET --> Obtained Key=" + key + " and Value="+ value + " from Cache server at localhost://300"+ keyvalue);
}
       // cache.put(1, "foo");
        //System.out.println("put(1 => foo)");

        //String value = cache.get(1);
        //System.out.println("get(1) => " + value);

        //System.out.println("Existing Cache Client...");
    }
}
