package Ejevika.socksstore.services;

import Ejevika.socksstore.models.Socks;
import Ejevika.socksstore.models.enums.Color;
import Ejevika.socksstore.models.enums.Size;

import java.util.List;

public interface SocksService {
    List<Socks> getAllSocks();

    Socks addSocks(Socks socks);
    int getSocks(Color color, Size size, int cottonMin, int cottonMax);
    boolean updateSocks(Socks socks);
    boolean removeSocks(Socks socks);
}
