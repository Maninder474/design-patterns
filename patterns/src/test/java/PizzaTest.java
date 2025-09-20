import com.opentext.patterns.factory.pizza.Pizza;
import com.opentext.patterns.factory.pizza.pizzaFactory.ChicagoPizzaStore;
import com.opentext.patterns.factory.pizza.pizzaFactory.NYPizzaStore;
import com.opentext.patterns.factory.pizza.pizzaFactory.PizzaStore;
import org.junit.jupiter.api.Test;

public class PizzaTest {

    @Test
    public void testPizza() {
        PizzaStore NYpizzaStore = new NYPizzaStore();
        PizzaStore ChicagoStore = new ChicagoPizzaStore();
        Pizza pizza = NYpizzaStore.orderPizza("cheese");
        System.out.println("Joel orderd a "+pizza.name);
        pizza = ChicagoStore.orderPizza("cheese");
        System.out.println("Ethan orderd a "+pizza.name);
    }
}
