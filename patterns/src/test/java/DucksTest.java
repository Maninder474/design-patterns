import com.opentext.patterns.duck.Duck;
import com.opentext.patterns.duck.MallardDuck;
import com.opentext.patterns.duck.RedHeadDuck;
import com.opentext.patterns.duck.RubberDuck;
import com.opentext.patterns.duck.behaviour.FlyNoFly;
import com.opentext.patterns.duck.behaviour.FlyWithRocket;
import com.opentext.patterns.duck.behaviour.Quack;
import org.junit.jupiter.api.Test;

public class DucksTest {

    @Test
    public void testDucks() {
        Duck duck1 = new RubberDuck();
        Duck duck2 = new MallardDuck();
        Duck duck3 = new RedHeadDuck();

        duck1.setFly(new FlyWithRocket());
        duck2.setFly(new FlyWithRocket());
        duck3.setFly(new FlyNoFly());

        duck1.setQuack(new Quack());
        duck2.setQuack(new Quack());
        duck3.setQuack(new Quack());

        duck1.perform();
        duck2.perform();
        duck3.perform();
    }
}
