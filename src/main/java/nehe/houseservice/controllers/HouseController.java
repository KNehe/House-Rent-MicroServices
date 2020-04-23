package nehe.houseservice.controllers;

import nehe.houseservice.models.House;
import nehe.houseservice.services.HouseService;
import nehe.houseservice.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/house-service")
public class HouseController {

    private HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {

        this.houseService = houseService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addHouse(@RequestBody House house){

        Objects.requireNonNull(house);

        House savedHouse = houseService.addHouse(house);

        if(savedHouse != null ){

            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body( CustomResponse.successResponse( savedHouse, "House added successfully") );
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomResponse.failResponse( "House not saved"));


    }

    @PatchMapping("/")
    public ResponseEntity<?>  editHouse(@RequestBody House house){

        Objects.requireNonNull(house);

        House editedHouse = houseService.addHouse(house);

        if(editedHouse != null ){

            return  ResponseEntity.status(HttpStatus.OK)
                    .body( CustomResponse.successResponse( editedHouse, "House edited successfully") );
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponse.failResponse( "House not edited"));


    }

    @DeleteMapping("/house/{id}")
    public ResponseEntity<?>  editHouse(@PathVariable UUID id){

        Objects.requireNonNull(id);

        if(houseService.deleteHouse(id)){

            return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponse.failResponse( "House not deleted"));

    }

    @GetMapping("/page/{page}/size/{size}")
    public  ResponseEntity<?>  getAllHouses(@PathVariable int page, @PathVariable int size){

        Page<House> data = houseService.getAllHouses(page, size);

        if( data != null ){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body( CustomResponse.successResponse( data, data.getNumberOfElements()+" Houses retrieved"));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( CustomResponse.failResponse( "An error occurred"));


    }

    @GetMapping("/bedrooms/{bedrooms}")
    public  ResponseEntity<?>  getAllHousesByNoOfBedRooms( @PathVariable int bedrooms ){

        List<House> data = houseService.getAllHousesByNoOfBedRooms(bedrooms);

        if( data != null ){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body( CustomResponse.successResponse( data, data.size()+" Houses retrieved"));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( CustomResponse.failResponse( "An error occurred"));


    }

    @GetMapping("/location/{location}")
    public  ResponseEntity<?>  getAllHousesByLocation( @PathVariable String location ){

        List<House> data = houseService.getAllHousesByLocation(location);

        if( data != null ){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body( CustomResponse.successResponse( data, data.size()+" Houses retrieved"));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( CustomResponse.failResponse( "An error occurred"));


    }

    @GetMapping("/total-rooms/{totalrooms}")
    public  ResponseEntity<?>  getAllHousesByTotalNoOfRooms( @PathVariable int totalrooms ){

        List<House> data = houseService.getAllHousesByTotalNoOfRooms(totalrooms);

        if( data != null ){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body( CustomResponse.successResponse( data, data.size()+" Houses retrieved"));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( CustomResponse.failResponse( "An error occurred"));

    }

    @GetMapping("/bedrooms/{bedrooms}/location/{location}")
    public  ResponseEntity<?>  getAllHouseNoOfBedRoomsAndLocation( @PathVariable int bedrooms ,  @PathVariable String location ){

        List<House> data = houseService.getAllHouseNoOfBedRoomsAndLocation( bedrooms,location);

        if( data != null ){
            return  ResponseEntity.status(HttpStatus.OK)
                    .body( CustomResponse.successResponse( data, data.size()+" Houses retrieved"));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body( CustomResponse.failResponse( "An error occurred"));


    }
}
