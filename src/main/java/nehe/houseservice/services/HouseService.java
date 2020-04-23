package nehe.houseservice.services;

import nehe.houseservice.models.House;
import nehe.houseservice.repositories.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Transactional
@Service
public class HouseService {

    private HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    //used for saving and editing
    public House addHouse(House house){

        return  houseRepository.save(house);
    }

    public boolean deleteHouse(UUID id){

        if( findHouseById(id) ){
            houseRepository.deleteById(id);
            return true;
        }
        return false;

    }

    public boolean findHouseById(UUID id){
         System.out.println("IDIDDDDDDDDDDDDD "+ id );
        House house  = houseRepository.findHouseById(id);
        System.out.println("house "+ house );
        return house != null;
    }

    public Page<House> getAllHouses(int page, int number_of_records_per_page){

        return houseRepository.findAll( PageRequest.of(page , number_of_records_per_page) );
    }

    public List<House> getAllHousesByNoOfBedRooms(int no_of_bedrooms ){

        return houseRepository.findAllByNoOfBedRooms( no_of_bedrooms );
    }

    public List<House> getAllHousesByLocation(String location ){

        return houseRepository.findAllByLocationContainingIgnoreCase( location );
    }

    public List<House> getAllHousesByTotalNoOfRooms(int total_no_of_rooms ){

        return houseRepository.findAllByTotalNoOfRooms( total_no_of_rooms );
    }

    public List<House> getAllHouseNoOfBedRoomsAndLocation(int no_of_bedrooms, String location ){

        return houseRepository.findAllByNoOfBedRoomsAndLocationContainingIgnoreCase(no_of_bedrooms, location);
    }

}
