package sessionproject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class Sessionproject {

    public static void main(String[] args) {
        
        CitiescsvDAO  D = new CitiescsvDAO();
        List<City>cities = D.readCitiesFromCsv("cities.csv");
       
        
        CountriescsvDAO  D2 = new CountriescsvDAO();
        List<country>countries = D2.readCountriesFromCsv("countries.csv");
     
        HashMap<String,List<City>> hm2 = new HashMap<String,List<City>>();
        HashMap<String,Integer> hm3 = new HashMap<String,Integer>();
      
       
        for(int i = 0 ; i<countries.size() ; i++){
            if(hm2.get(countries.get(i).getCode()) == null){
                List<City> lsC = new ArrayList<>();
                for(int j = 0 ; j<cities.size() ; j++){
                    if(countries.get(i).getCode().equals(cities.get(j).getCountry_id())){
                        
                        City c = cities.get(j);
                        lsC.add(c);
                        
                    }
                }
                
                lsC.sort(Comparator.comparing( City::getPop) );
                hm2.put(countries.get(i).getCode(), lsC);
                
                     ///////Stream High POP City  ////////////////
           Optional<String>High_pop_city = lsC.stream()
                                          .max(Comparator.comparing(City::getPop))
                                          .map(City::getCity);
           
       System.out.println("Coutnry: "+"("+countries.get(i).getCountry()+")"+ 
            ", Highest POP city: " +"("+High_pop_city.get()+")");
                       //////////////////////////////
                       
        /////////////////// Stream High POP Capital //////////////////////
      
  Map<String,List<City>>list_of_capital= lsC.stream().
                collect(Collectors.groupingBy(City::getCapital));
        
   list_of_capital.forEach((c , ls)->{
     
   if(c.equals("primary")){
   for(City x : ls){
       hm3.put(x.getCity(), x.getPop());
   }
   }
   });
                
        }  
    }      
     /*  for(Map.Entry en : hm3.entrySet()){
           System.out.println(en.getKey() + " " + en.getValue());
       }*/
       HashMap<String, Integer> temp
            = hm3.entrySet()
                  .stream()
                  .sorted((i1, i2)
                              -> i1.getValue().compareTo(
                                  i2.getValue()))
                  .collect(Collectors.toMap(
                      Map.Entry::getKey,
                      Map.Entry::getValue,
                      (e1, e2) -> e1, LinkedHashMap::new));
 
       System.out.println("Highest population capital is ");
       System.out.println(temp.entrySet().toArray()[temp.size()-1]);
        
    
}
    
}