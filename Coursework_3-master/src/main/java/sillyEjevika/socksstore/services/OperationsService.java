package sillyEjevika.socksstore.services;

import sillyEjevika.socksstore.models.enums.OperationType;
import sillyEjevika.socksstore.models.Socks;

public interface OperationsService {
    void addOperation(OperationType type, Socks socks);
}
