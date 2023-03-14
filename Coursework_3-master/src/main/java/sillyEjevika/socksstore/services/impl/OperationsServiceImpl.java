package sillyEjevika.socksstore.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import sillyEjevika.socksstore.models.Operation;
import sillyEjevika.socksstore.models.enums.OperationType;
import sillyEjevika.socksstore.models.Socks;
import sillyEjevika.socksstore.services.FilesService;
import sillyEjevika.socksstore.services.OperationsService;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class OperationsServiceImpl implements OperationsService {

    private List<Operation> operations = new LinkedList<>();

    private final FilesService filesService;
    private final ObjectMapper mapper;

    public OperationsServiceImpl(FilesService filesService) {
        this.filesService = filesService;
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Override
    public void addOperation(OperationType type, Socks socks) {
        operations.add(new Operation(type, socks.getQuantity(), socks.getSize(), socks.getCottonRel(), socks.getColor()));
        saveToFile();
    }

    private void saveToFile() {
        try {
            String json = mapper.writeValueAsString(operations);
            filesService.saveOperationsToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        String json = filesService.readOperationsFromFile();
        try {
            if (!json.isBlank()) {
                operations = mapper.readValue(json, new TypeReference<List<Operation>>() {
                });
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init() {
        readFromFile();
    }
}
