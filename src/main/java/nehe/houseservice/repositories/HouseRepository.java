package nehe.houseservice.repositories;

import nehe.houseservice.models.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface HouseRepository  extends JpaRepository<House, UUID> {

    Page<House> findAll(Pageable pageable);

    @Query( value= " SELECT * FROM house WHERE  no_of_bed_rooms = ?1", nativeQuery = true )
    List<House> findAllByNoOfBedRooms(int noOfBedrooms);

    List<House> findAllByLocationContainingIgnoreCase(String location);

    @Query( value= " SELECT * FROM house WHERE  id = ?1", nativeQuery = true )
    House findHouseById(UUID id);

    List<House> findAllByTotalNoOfRooms(int total_no_of_rooms);

    List<House> findAllByNoOfBedRoomsAndLocationContainingIgnoreCase(int total_no_of_rooms , String location);


}
