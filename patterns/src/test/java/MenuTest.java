import com.opentext.patterns.collections.Composite.MenuComponent;
import com.opentext.patterns.collections.DinerMenu;
import com.opentext.patterns.collections.Menu;
import com.opentext.patterns.collections.MenuItem;
import com.opentext.patterns.collections.PancakeHouseMenu;
import com.opentext.patterns.collections.Waitress;
import com.opentext.patterns.collections.cafeMenu;
import org.junit.jupiter.api.Test;

public class MenuTest {
    @Test
    public void MenuTestDrive() {
        Menu pancakeHouseMenu = new PancakeHouseMenu();
        Menu dinerMenu = new DinerMenu();
        Menu cafeMenu = new cafeMenu();

        Waitress waitress = new Waitress(pancakeHouseMenu, dinerMenu,cafeMenu);
        waitress.printMenu();


    }
}
