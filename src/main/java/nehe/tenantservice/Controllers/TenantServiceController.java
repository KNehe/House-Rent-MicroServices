package nehe.tenantservice.Controllers;

import nehe.tenantservice.Proxys.HouseServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenant-service")
public class TenantServiceController {

    private HouseServiceProxy houseServiceProxy;

    @Autowired
    public TenantServiceController(HouseServiceProxy houseServiceProxy) {
        this.houseServiceProxy = houseServiceProxy;
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> getHouseByLocation(@PathVariable String location){

        return   houseServiceProxy.getHouseByLocation(location);
    }

    @GetMapping("/house/{id}")
    public ResponseEntity<?> getHouseByById(@PathVariable Long id){

        return   houseServiceProxy.getHouseById(id);
    }

    @GetMapping("/bedrooms/{bedrooms}")
    public ResponseEntity<?> getHouseByNoOfBedrooms(@PathVariable int bedrooms ){

        return   houseServiceProxy.getHouseByNoOfBedrooms( bedrooms );
    }


    @GetMapping("/total-rooms/{totalrooms}")
    public ResponseEntity<?> getHouseByTotalNoOfRooms(@PathVariable int totalrooms ){

        return   houseServiceProxy.getHouseByTotalNoOfRooms(totalrooms);
    }

    @GetMapping("/bedrooms/{bedrooms}/location/{location}")
    ResponseEntity<?> getAllHouseNoOfBedRoomsAndLocation(@PathVariable int bedrooms , @PathVariable  String location){

        return   houseServiceProxy.getAllHouseNoOfBedRoomsAndLocation( bedrooms, location );

    }

    @GetMapping("/page/{page}/size/{size}")
    ResponseEntity<?>  getAllHouses(@PathVariable int page, @PathVariable int size){

        return  houseServiceProxy.getAllHouses( page, size );

    }
}
