import java.util.*;
public class Restaurant{
    PriorityQueue<Integer> driverReturnTime = new PriorityQueue<>();
    int numberOfDrivers;
    int curTime = 0;
    String restaurantName = "unnamed";
    // Time of finished cooking , Order Number
    PriorityQueue<AbstractMap.SimpleEntry<Integer, Integer>> oven = 
        new PriorityQueue<>((a,b) -> Integer.compare(a.getKey(), b.getKey()));
    // Map of menu and cooking time
    Map<String, Integer> cookingTime = new HashMap<>();
    // List of Menu
    List<String> menuList = new ArrayList<>();
    // Set and get restaurant name
    public void setName(String name){
        this.restaurantName = name;
    }
    public String getName(){
        return restaurantName;
    }
    // sets number of drivers and adds them to driver pq
    public void setDrivers(int num){
        this.numberOfDrivers = num;
        for(int i = 0; i < numberOfDrivers; i++){
            driverReturnTime.offer(0);
        }
    }
    // retrieves number of drivers
    public int getDrivers(){
        return numberOfDrivers;
    }
    // adds item to menu along with the time it takes to cook
    public void addItemToMenu(String item, int time){
        cookingTime.put(item, time);
        menuList.add(item);
    }
    public Map<String, Integer> getCookingTimes(){
        return cookingTime;
    }
    public List<String> getMenu(){
        return menuList;
    }
    // returns the time it takes to cook a specified item
    public int retrieveItemCookingTime(String item){
        if(!cookingTime.containsKey(item)){
            return -1;
        }
        return cookingTime.get(item);
    }
    // adds an item (its time to cook) and the order number associated with that item to oven pq
    public void cookItem(String item, int orderNumber){
        int timeWhenFinishedCooking = curTime + cookingTime.get(item);
        AbstractMap.SimpleEntry<Integer, Integer> pair = 
            new AbstractMap.SimpleEntry<>(timeWhenFinishedCooking, orderNumber);
        oven.offer(pair);
    }
    // gets the next time of food cooked 
    public int getNextCookingTime(){
        return oven.peek().getKey();
    }
    // check how many items are in oven
    public int getOvenItemsQuantity(){
        return oven.size();
    }
    //checks if food in oven is cooked
    public boolean isFoodReady(){
        // if(oven.isEmpty()) return false;
        return oven.peek().getKey() <= curTime;
    }
    //returns order number of "cooked" food (will work in conjunction with above method)
    public int takeOutOfOvenGetOrderNumber(){
        return oven.poll().getValue();
    }
    // checks if theres an available driver
    public boolean isDriverAvailable(){
        return driverReturnTime.peek() <= curTime;
    }
    // checks time when nearest driver will arrive
    public int getNearestDriverTime(){
        return driverReturnTime.peek();
    }
    // returns when the next even will happen
    public int getNextEvent(){
        int eventDriverTime = driverReturnTime.peek() > curTime ? driverReturnTime.peek() : 100000;
        return Math.min(oven.peek().getKey(), eventDriverTime);
    }
    // goes to time period of next event (when food is cooked or when driver arrives at restaurant)
    public void goToNextEvent(){
        int eventDriverTime = Integer.MAX_VALUE;
        if(!driverReturnTime.isEmpty() && driverReturnTime.peek() > curTime) eventDriverTime = driverReturnTime.peek();
        int eventCookTime = Integer.MAX_VALUE;
        if(!oven.isEmpty() && oven.peek().getKey() > curTime) eventCookTime = oven.peek().getKey();
        curTime = Math.min(eventCookTime, eventDriverTime);
    }
    // sends out delivery driver with orders and the time it takes to deliver
    public void sendOutDriver(int time){
        driverReturnTime.remove();
        driverReturnTime.offer(time + curTime);
    }
    public int getTime(){
        return curTime;
    }
}