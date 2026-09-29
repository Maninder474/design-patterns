import com.opentext.patterns.collections.Composite.MenuComponent;
import com.opentext.patterns.collections.Composite.Menu;
import com.opentext.patterns.collections.MenuItem;
import com.opentext.patterns.collections.CompositeWaitress;
import org.junit.jupiter.api.Test;

public class CompositeMenuTest {
    @Test
    public void CompositeMenuTestDrive() {
        MenuComponent panCakeHouseMenuComponent = new Menu("PANCAKE HOUSE MENU", "Breakfast");
        MenuComponent dinerMenuComponent = new Menu("DINER MENU", "Lunch");
        MenuComponent cafeMenuComponent = new Menu("CAFE MENU", "Dinner");
        MenuComponent dessertMenuComponent = new Menu("DESSERT MENU", "Dessert of course!");

        MenuComponent allMenus = new Menu("ALL MENUS", "All menus combined");
        allMenus.add(panCakeHouseMenuComponent);
        allMenus.add(dinerMenuComponent);
        allMenus.add(cafeMenuComponent);

        cafeMenuComponent.add(new MenuItem("Veggie Burger and Air Fries", "Veggie burger on a whole wheat bun, lettuce, tomato, and fries", true, 3.99));
        cafeMenuComponent.add(new MenuItem("Soup of the day", "A cup of the soup of the day, with a side salad", false, 3.69));
        cafeMenuComponent.add(new MenuItem("Burrito", "A large burrito, with whole pinto beans, salsa, guacamole", true, 4.29));

        panCakeHouseMenuComponent.add(new MenuItem("K&B's Pancake Breakfast", "Pancakes with scrambled eggs, and toast", true, 2.99));
        panCakeHouseMenuComponent.add(new MenuItem("Regular Pancake Breakfast", "Pancakes with fried eggs, sausage", false, 2.99));
        panCakeHouseMenuComponent.add(new MenuItem("Blueberry Pancakes", "Pancakes made with fresh blueberries", true, 3.49));

        dinerMenuComponent.add(new MenuItem("Pasta", "Spaghetti with Marinara Sauce, and a slice of sourdough bread", true, 3.89));
        dinerMenuComponent.add(dessertMenuComponent);
        dessertMenuComponent.add(new MenuItem("Apple Pie", "Apple pie with a flakey crust, topped with vanilla ice cream", true, 1.59));
        dessertMenuComponent.add(new MenuItem("Cheesecake", "Creamy New York cheesecake, with a chocolate graham cracker crust", true, 1.99));
        dessertMenuComponent.add(new MenuItem("Sorbet", "A scoop of raspberry and a scoop of lime", true, 1.89));
        // Create a waitress and print the menu
        CompositeWaitress waitress = new CompositeWaitress(allMenus);
        waitress.printMenu();
        waitress.printVegetarianMenu();
    }
}
