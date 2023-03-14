package sillyEjevika.socksstore.services;

import sillyEjevika.socksstore.models.enums.Color;
import sillyEjevika.socksstore.models.enums.Size;
import sillyEjevika.socksstore.models.Socks;

import java.util.List;

public interface SocksService {
    List<Socks> getAllSocks();

    Socks addSocks(Socks socks);
    int getSocks(Color color, Size size, int cottonMin, int cottonMax);
    boolean updateSocks(Socks socks);
    boolean removeSocks(Socks socks);
}
