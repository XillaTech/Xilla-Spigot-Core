import lombok.Getter;
import lombok.Setter;
import net.xilla.core.library.manager.StoredData;
import net.xilla.spigotcore.util.SpigotSettings;

public class Test extends SpigotSettings {

    public static void main(String[] args) {

    }

    @StoredData
    @Getter
    @Setter
    public String setting = "value";

    public Test() {
        super("/test-file.json");
    }

}
