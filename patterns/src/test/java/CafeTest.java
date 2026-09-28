import com.opentext.patterns.template.Coffee;
import com.opentext.patterns.template.Tea;
import com.opentext.patterns.template.CaffeineBeverage;
import org.junit.jupiter.api.Test;

public class CafeTest {
    @Test
    public void testCafe() {
        CaffeineBeverage tea = new Tea();
        tea.prepareRecipe();

        System.out.println();

        CaffeineBeverage coffee = new Coffee();
        coffee.prepareRecipe();

    }
}
