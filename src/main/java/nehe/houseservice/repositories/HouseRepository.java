package nehe.houseservice.repositories;

import nehe.houseservice.models.House;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface HouseRepository  extends PagingAndSortingRepository<House, Long> {

    List<House> findAllByNoOfBedRooms(int noOfBedrooms);

    List<House> findAllByLocationContainingIgnoreCase(String location);

    House findHouseById(Long id);

    List<House> findAllByTotalNoOfRooms(int total_no_of_rooms);

    List<House> findAllByNoOfBedRoomsAndLocationContainingIgnoreCase(int total_no_of_rooms , String location);


}
