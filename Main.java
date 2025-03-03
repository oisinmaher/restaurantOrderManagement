import java.util.*;

public class Main {
    static List<Restaurant> restaurantOptions = new ArrayList<>();
    public static void main(String[] args) {
        restrauntSide();
        customerSide();
    }
    public static void customerSide(){
        Scanner sc = new Scanner(System.in);
        System.out.println("What restaurant would you like to Order From");
        for(int i = 0; i < restaurantOptions.size(); i++){
            System.out.println((i+1) + ":" + restaurantOptions.get(i).getName() + ".");
        }
        int resOption = sc.nextInt();
        Restaurant restaurant  = restaurantOptions.get(resOption-1);
        // print menu
        List<String> menuList = restaurant.getMenu();
        List<Integer> orderItems = new ArrayList<>();
        boolean orderAnother = true;
        while(orderAnother){
            addItemsToOrder(menuList, orderItems);
            System.out.println("Would you like to order more items?\n1. Yes 2. No");
            if(sc.nextInt() == 2)orderAnother = false;
        }
    }
    public static void addItemsToOrder(List<String> menuList, List<Integer> orderItems){
        Scanner sc = new Scanner(System.in);
        System.out.println("What item would you like to order");
        for(int i = 0; i < menuList.size(); i++){
            System.out.println((i+1) + ": " + menuList.get(i));
        }
        int choice = sc.nextInt();
        orderItems.add(choice-1);
    }
    public static void restrauntSide(){
        Restaurant restaurant;
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to\n1. Choose Restaurant\n2. Create Restaurant");
        int loginChoice = sc.nextInt();
        if(loginChoice == 1){
            System.out.println("What restaurant are you?");
            for(int i = 0; i < restaurantOptions.size(); i++){
                System.out.println((i+1) + restaurantOptions.get(i).getName());
            }
            int resOption = sc.nextInt() -1;
            restaurant = restaurantOptions.get(resOption);
            System.out.println("Logged in as "+restaurant.getName());
        }
        else{
            restaurant = new Restaurant();
            restaurantOptions.add(restaurant);
            System.out.println("Enter your restaurant's name");
            String restaurantName = sc.next();
            restaurant.setName(restaurantName);
        }
        System.out.println("Welcome "+restaurant.getName() + " admin");
        boolean exitRestaurant = false;
        String[] adminOptions = new String[]{"Check time","Add item to menu","Check number of delivery drivers", "Change number of delivery drivers","Check when next driver is available", "Check items on menu", "Check menu and cooking times","Check how many items are in oven","Put an item in oven", "Check if an item is cooked","Check when next item will be cooked","Change restaurant name","Take item out of oven and Retrieve Order Number","Go to Next Event", "Exit"};
        while(!exitRestaurant){
            System.out.println("What would you like to do?");
            for(int i = 0; i < adminOptions.length; i++){
                System.out.println((i+1) + " " + adminOptions[i]);
            }
            int adminChoice = sc.nextInt();
            switch(adminChoice){
                case 1 -> restaurantCheckTime(restaurant);
                case 2 -> restaurantAddItemToMenu(restaurant);
                case 3 -> restaurantCheckDrivers(restaurant);
                case 4 -> restaurantChangeDrivers(restaurant);
                case 5 -> restaurantCheckDriverAvailability(restaurant);
                case 6 -> restaurantCheckMenuItems(restaurant);
                case 7 -> restaurantCheckCookTimes(restaurant);
                case 8 -> restaurantCheckOvenQuantity(restaurant);
                case 9 -> restaurantAddItemToOven(restaurant);
                case 10 -> restaurantCheckIfAnItemIsCooked(restaurant);
                case 11 -> restaurantCheckWhenItemWillBeCooked(restaurant);
                case 12 -> restaurantChangeName(restaurant);
                case 13 -> restaurantTakeOutOfOven(restaurant);
                case 14 -> restaurantGoToNextEvent(restaurant);
                default -> {
                    System.out.println("Exiting Restaurant Admin Panel, Thanks for Using!");
                    exitRestaurant = true;
                }
            }
            if(!exitRestaurant){
                System.out.println("Go back to menu?\n1.Ok");
                int choice = sc.nextInt();
            }
        }
    }
    public static void restaurantCheckTime(Restaurant restaurant){
        System.out.println("The current global time is " + restaurant.getTime());
    }
    public static void restaurantAddItemToMenu(Restaurant restaurant){
        Scanner sc = new Scanner(System.in);
        System.out.println("What would you like to add your menu");
        String itemName = sc.nextLine();
        System.out.println("How long will this item take to cook in minutes");
        int cookTime = sc.nextInt();
        restaurant.addItemToMenu(itemName, cookTime);
    }
    public static void restaurantCheckDrivers(Restaurant restaurant){
        System.out.println("The current number of drivers are "+ restaurant.getDrivers());
    }
    public static void restaurantChangeDrivers(Restaurant restaurant){
        Scanner sc = new Scanner(System.in);
        System.out.println("How many drivers do you have working for you");
        int drivers = sc.nextInt();
        restaurant.setDrivers(drivers);
        System.out.println("Updated drivers to: " + drivers);
    }
    public static void restaurantCheckDriverAvailability(Restaurant restaurant){
        System.out.println("The next driver will be available at " + restaurant.getNearestDriverTime() + " global time which is in " + (restaurant.getNearestDriverTime() - restaurant.getTime()) + " minutes.");
    }
    public static void restaurantCheckMenuItems(Restaurant restaurant){
        List<String> menu = restaurant.getMenu();
        for(int i = 0; i < menu.size(); i++){
            System.out.println((i+1) + ": " + menu.get(i));
        }
    }
    public static void restaurantCheckCookTimes(Restaurant restaurant){
        Map<String, Integer> cookTimes = restaurant.getCookingTimes();
        List<String> menu = restaurant.getMenu();
        for(int i = 0; i < menu.size(); i++){
            System.out.printf("%d. %-15s: %d%s%n",i+1,menu.get(i),cookTimes.get(menu.get(i)), " minutes");
        }
    }
    public static void restaurantCheckOvenQuantity(Restaurant restaurant){
        System.out.println("There are " + restaurant.getOvenItemsQuantity() + " items in oven.");
    }
    public static void restaurantAddItemToOven(Restaurant restaurant){
        List<String> menu = restaurant.getMenu();
        Scanner sc = new Scanner(System.in);
        System.out.println("What would you like to put in the oven");
        restaurantCheckCookTimes(restaurant);
        int menuOption = sc.nextInt();
        System.out.println("What is the order number");
        int orderNo = sc.nextInt();
        restaurant.cookItem(menu.get(menuOption-1), orderNo);
        System.out.println("Added " + menu.get(menuOption-1) + " to oven in Order Number " + orderNo);
    }
    public static void restaurantCheckIfAnItemIsCooked(Restaurant restaurant){
        if(restaurant.isFoodReady()){
            System.out.println("Yes, there is an item currently cooked in oven");
        }
        else System.out.println("No items in oven are currently cooked");
    }
    public static void restaurantCheckWhenItemWillBeCooked(Restaurant restaurant){
        System.out.println("The next item will be ready at the time " + restaurant.getNextCookingTime() + " which is in " + (restaurant.getNextCookingTime()- restaurant.getTime()) + " minutes.");
    }
    public static void restaurantTakeOutOfOven(Restaurant restaurant){
        int orderNumber = restaurant.takeOutOfOvenGetOrderNumber();
        System.out.println("Taken a Cooked item out of oven its Order Number is " + orderNumber);
    }
    public static void restaurantSendOutOrder(Restaurant restaurant){
        
    }
    public static void restaurantGoToNextEvent(Restaurant restaurant){
        System.out.println("Going to next event");
        int oldTime = restaurant.getTime();
        restaurant.goToNextEvent();
        int curTime = restaurant.getTime();
        System.out.println("Old time " + oldTime + " current time " + curTime);
    }
    public static void restaurantChangeName(Restaurant restaurant){
        Scanner sc = new Scanner(System.in);
        String curName = restaurant.getName();
        System.out.println("What would you like to change your restaurant name to? (Leave blank for no change)");
        String newName = sc.next();
        if(newName.equals("") || newName.equals(curName)){
            System.out.println("Not changing name!");
        }
        else{
            restaurant.setName(newName);
            System.out.println("Your restaurant's new name is " + restaurant.getName());
        }
    }
}
