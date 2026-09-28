import com.opentext.patterns.duck.Duck;
import com.opentext.patterns.duck.MallardDuck;
import com.opentext.patterns.duck.RedHeadDuck;
import com.opentext.patterns.duck.RubberDuck;
import com.opentext.patterns.duck.Turkey;
import com.opentext.patterns.duck.TurkeyAdapter;
import com.opentext.patterns.duck.WildTurkey;
import com.opentext.patterns.duck.behaviour.FlyNoFly;
import com.opentext.patterns.duck.behaviour.FlyWithRocket;
import com.opentext.patterns.duck.behaviour.Quack;
import com.opentext.patterns.duck.behaviour.MuteQuack;
import com.opentext.patterns.duck.behaviour.Squeak;
import org.junit.jupiter.api.Test;

public class DucksTest {

    @Test
    public void testDucks() {
        Duck duck1 = new RubberDuck();
        Duck duck2 = new MallardDuck();
        Duck duck3 = new RedHeadDuck();
        Turkey turkey = new WildTurkey();

        duck1.setFly(new FlyWithRocket());
        duck2.setFly(new FlyWithRocket());
        duck3.setFly(new FlyNoFly());

        duck1.setQuack(new Quack());
        duck2.setQuack(new Squeak());
        duck3.setQuack(new MuteQuack());

        duck1.perform();
        duck2.perform();
        duck3.perform();

        System.out.println("The Turkey says:");
        turkey.gobble();
        turkey.fly();

        System.out.println("Now the Turkey is adapted to a Duck:");
        Duck turkeyAdapter = new TurkeyAdapter(turkey);
        turkeyAdapter.display();


    }
}
