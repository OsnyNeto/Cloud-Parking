package SpringBoot.CloudParking.service;

import SpringBoot.CloudParking.exception.ParkingNotFoundException;
import SpringBoot.CloudParking.model.Parking;
import SpringBoot.CloudParking.repository.ParkingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ParkingService {

//    private static Map<String, Parking> parkingMap = new HashMap();

    //    static {
//        var id = getUUID();
//        var id1 = getUUID();
//        var id2 = getUUID();
//        Parking parking = new Parking(id, "IUO-0B95", "RS", "FIESTA", "PRATA");
//        Parking parking1 = new Parking(id1, "JAY-7J50", "SC", "GS 310 ", "PRETA");
//        Parking parking2 = new Parking(id2, "IZX-9H18", "AM", "MT 03  ", "CINZA");
//        parkingMap.put(id, parking);
//        parkingMap.put(id1, parking1);
//        parkingMap.put(id2, parking2);
//    }
    private final ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Transactional //(readOnly = true, propagation = Propagation.SUPPORTS) -> não funcionou
    public List<Parking> findAll() {
        return parkingRepository.findAll();

//        Simulando com dados mocados
//        return parkingMap.values().stream().collect(Collectors.toList());
    }

    @Transactional  //(readOnly = true) -> não funcionou
    public Parking findById(String id) {

        return parkingRepository.findById(id).orElseThrow(() ->
                new ParkingNotFoundException(id));

//        Simulando com dados mocados
//        Parking parking = parkingMap.get(id);
//        if (parking == null) {
//            throw new ParkingNotFoundException(id);
//        }
//        return parking;
    }

    @Transactional
    public Parking create(Parking parkingCreate) {
        String uuid = getUUID();
        parkingCreate.setId(uuid);
        parkingCreate.setEntryDate(LocalDateTime.now());
        parkingRepository.save(parkingCreate);

//        parkingMap.put(uuid, parkingCreate);
        return parkingCreate;
    }

    @Transactional
    public void delete(String id) {
        findById(id);
        parkingRepository.deleteById(id);
//        parkingMap.remove(id);
    }

    @Transactional
    public Parking update(String id, Parking parkingCreate) {
        Parking parking = findById(id);
        parking.setColor(parkingCreate.getColor());
        parking.setState(parkingCreate.getState());
        parking.setModel(parkingCreate.getModel());
        parking.setLicense(parkingCreate.getLicense());
        parkingRepository.save(parking);

//        parkingMap.replace(id, byId);
        return parking;

    }

    @Transactional
    public Parking exit(String id) {
        //Recuperar o estacionado
        Parking parking = findById(id);
        //Altgerar o horário de saída
        parking.setExitDate(LocalDateTime.now());
        //Fazer o cálculo
        parking.setBill(ParkingFechaConta.getBill(parking));
        parkingRepository.save(parking);
        return parking;
    }
}
