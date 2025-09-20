import com.opentext.patterns.command.items.GarageDoor;
import com.opentext.patterns.command.items.Light;
import com.opentext.patterns.command.items.Stereo;
import com.opentext.patterns.command.remote.Command;
import com.opentext.patterns.command.remote.GarageDoorCommand;
import com.opentext.patterns.command.remote.LightOnCommand;
import com.opentext.patterns.command.remote.RemoteControl;
import com.opentext.patterns.command.remote.SimpleRemoteControl;
import com.opentext.patterns.command.remote.StereoCommand;
import org.junit.jupiter.api.Test;

public class RemoteTest {
    @Test
    public void testRemote() {
        SimpleRemoteControl remote = new SimpleRemoteControl();
        RemoteControl remoteControl = new RemoteControl();
        Light light = new Light();
        Command command1 = new LightOnCommand(light);
        GarageDoor garageDoor = new GarageDoor();
        Command command2 = new GarageDoorCommand(garageDoor);
        Stereo stereo = new Stereo("Home System stereo");
        Command command3 = new StereoCommand(stereo);
        remote.setSlot(command1);
        remote.buttonPressed();
        remote.setSlot(command2);
        remote.buttonPressed();
        remoteControl.setCommand(0,command1);
        remoteControl.setCommand(1,command2);
        remoteControl.setCommand(2,command3);
        remoteControl.buttonWasPressed(0);
        remoteControl.buttonWasPressed(1);
        remoteControl.buttonWasPressed(2);
    }
}
