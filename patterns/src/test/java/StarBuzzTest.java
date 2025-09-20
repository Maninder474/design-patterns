import com.opentext.patterns.decorator.starbuzz.Beverage;
import com.opentext.patterns.decorator.starbuzz.Espresso;
import com.opentext.patterns.decorator.starbuzz.decorators.Mocha;
import com.opentext.patterns.decorator.starbuzz.decorators.Whip;
import org.junit.jupiter.api.Test;

public class StarBuzzTest {

    @Test
    public void testStarBuzz() {
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDescription());
        System.out.println(beverage.getCost());
        beverage = new Mocha(beverage);
        System.out.println(beverage.getDescription());
        System.out.println(beverage.getCost());
        beverage = new Whip(beverage);
        System.out.println(beverage.getDescription());
        System.out.println(beverage.getCost());
    }
}
