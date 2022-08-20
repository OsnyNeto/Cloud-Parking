package dio.me.CloudParking.controller;

import dio.me.CloudParking.controller.mapper.ParkingMapper;
import dio.me.CloudParking.controller.dto.ParkingDTO;
import dio.me.CloudParking.model.Parking;
import dio.me.CloudParking.service.ParkingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;

    //O construtor substitui o Autowired e é boas praticas fazer pelo construtor.
    //Padrão indicado para fazer injeção de depêndencia.
    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    public List<ParkingDTO> findAll(){

        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
        return result;

    }
}
