package app.chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        Map<String,Integer> cars = new HashMap<>();{
        };


        cars.put("BMW", 3);



        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

//        for (String car : new HashSet<>(cars)) {
//            System.out.println(car);
//            cars.add("toyota");
//        }
        cars.put("Audi", 2);
        System.out.println(cars);


    }
}