package Ejevika.socksstore.services;

import Ejevika.socksstore.models.Socks;
import Ejevika.socksstore.models.enums.OperationType;

public interface OperationsService {
    void addOperation(OperationType type, Socks socks);
}
