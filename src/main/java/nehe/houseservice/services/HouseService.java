package nehe.houseservice.services;

import nehe.houseservice.models.House;
import nehe.houseservice.repositories.HouseRepository;
import nehe.houseservice.utils.HouseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    public boolean deleteHouse(Long id) throws  HouseNotFoundException{

        houseRepository.findById(id)
                .orElseThrow( ()-> new HouseNotFoundException(String.format("House with id %s not found", id)) );

            houseRepository.deleteById(id);
            return true;

    }

    public Page<House> getAllHouses(int page, int number_of_records_per_page){

        Pageable pageable = PageRequest.of( page, number_of_records_per_page , Sort.by(Sort.Direction.DESC, "id") );

       return  houseRepository.findAll(pageable);

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

    public House getHouseById(Long id) throws HouseNotFoundException {

        String message = String.format("House %s not found ", id);

        return houseRepository.findById(id).orElseThrow( ()-> new HouseNotFoundException(message) );
    }

}
