package com.example.springdatabasicdemo.init;

import com.iamruda.contractfirst.dtos.*;
import com.example.springdatabasicdemo.services.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private InspectedPersonService inspectedPersonService;
    private CargoService cargoService;
    private TruckService truckService;
    private InspectorService inspectorService;
    private InspectionService inspectionService;
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public CommandLineRunnerImpl(InspectedPersonService inspectedPersonService,
                                 CargoService cargoService,
                                 TruckService truckService,
                                 InspectorService inspectorService,
                                 InspectionService inspectionService,
                                 RabbitTemplate rabbitTemplate) {
        this.inspectedPersonService = inspectedPersonService;
        this.cargoService = cargoService;
        this.truckService = truckService;
        this.inspectorService = inspectorService;
        this.inspectionService = inspectionService;

        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        // rabbitTest();
    }

    private void seedData() {
        InspectedPersonDTO person1 = new InspectedPersonDTO(1L, "Ivan Ivanov", "1234567890", null);
        InspectedPersonDTO person2 = new InspectedPersonDTO(2L, "Petr Petrov", "9876543210", null);

        inspectedPersonService.register(person1);
        inspectedPersonService.register(person2);

        TruckDTO truck1 = new TruckDTO(1L, "Volvo", "FH16", "A123BC777", 2018, new HashSet<>(), null);
        TruckDTO truck2 = new TruckDTO(2L, "MAN", "TGX", "B456DE999", 2015, new HashSet<>(), null);

        truckService.register(truck1);
        truckService.register(truck2);

        CargoDTO cargo1 = new CargoDTO(1L, 50.5, 3.2, Set.of("Товар 1", "Товар 2"), truck1.getId(), null);
        CargoDTO cargo2 = new CargoDTO(2L, 75.0, 5.0, Set.of("Груз 1", "Груз 2", "Груз 3"), truck2.getId(), null);

        cargoService.register(cargo1);
        cargoService.register(cargo2);

        InspectorDTO inspector1 = new InspectorDTO(1L, "Sidorov Andrey", "001");
        InspectorDTO inspector2 = new InspectorDTO(2L, "Kusnecov Sergey", "002");

        inspectorService.register(inspector1);
        inspectorService.register(inspector2);

        InspectionDTO inspection1 = new InspectionDTO(1L, inspector1, person1, truck1, new java.sql.Date(System.currentTimeMillis()), "Завершено", null);
        InspectionDTO inspection2 = new InspectionDTO(2L, inspector2, person2, truck2, new java.sql.Date(System.currentTimeMillis()), "В процессе", null);

        inspectionService.register(inspection1);
        inspectionService.register(inspection2);
    }


}
