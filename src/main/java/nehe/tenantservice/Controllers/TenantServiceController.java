package nehe.tenantservice.Controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import nehe.tenantservice.Proxys.HouseServiceProxy;
import nehe.tenantservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenant-service")
@RefreshScope
public class TenantServiceController {

    private HouseServiceProxy houseServiceProxy;
    private GetAllHousesInfo getAllHousesInfo;
    private GetHouseByLocationInfo getHouseByLocationInfo;
    private GetHousesByIdInfo getHousesByIdInfo;
    private GetBedroomInfo getBedroomInfo;
    private GetNoOfRoomsInfo getNoOfRoomsInfo;
    private GetHouseByBedRoomAndLocationInfo houseByBedRoomAndLocationInfo;

    @Autowired
    public TenantServiceController(HouseServiceProxy houseServiceProxy, GetAllHousesInfo getAllHousesInfo,
                                   GetHouseByLocationInfo getHouseByLocationInfo,
                                   GetHousesByIdInfo getHousesByIdInfo, GetBedroomInfo getBedroomInfo,
                                   GetNoOfRoomsInfo getNoOfRoomsInfo ,
                                   GetHouseByBedRoomAndLocationInfo houseByBedRoomAndLocationInfo) {
        this.houseServiceProxy = houseServiceProxy;
        this.getAllHousesInfo = getAllHousesInfo;
        this.getHouseByLocationInfo = getHouseByLocationInfo;
        this.getHousesByIdInfo = getHousesByIdInfo;
        this.getBedroomInfo = getBedroomInfo;
        this.getNoOfRoomsInfo = getNoOfRoomsInfo;
        this.houseByBedRoomAndLocationInfo = houseByBedRoomAndLocationInfo;
    }

    @GetMapping("/page/{page}/size/{size}")
    ResponseEntity<?>  getAllHouses(@PathVariable int page, @PathVariable int size){

        return  getAllHousesInfo.getAllHouses( page, size );

    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> getHouseByLocation(@PathVariable String location){

        return   getHouseByLocationInfo.getHouseByLocation(location);
    }

    @GetMapping("/house/{id}")
    public ResponseEntity<?> getHouseByById(@PathVariable Long id){

        return   getHousesByIdInfo.getHouseById(id);
    }

    @GetMapping("/bedrooms/{bedrooms}")
    public ResponseEntity<?> getHouseByNoOfBedrooms(@PathVariable int bedrooms ){

        return   getBedroomInfo.getHouseByTotalNoOfRooms( bedrooms );
    }


    @GetMapping("/total-rooms/{totalrooms}")
    public ResponseEntity<?> getHouseByTotalNoOfRooms(@PathVariable int totalrooms ){

        return   getNoOfRoomsInfo.getHouseByTotalNoOfRooms(totalrooms);
    }

    @GetMapping("/bedrooms/{bedrooms}/location/{location}")
    ResponseEntity<?> getAllHouseNoOfBedRoomsAndLocation(@PathVariable int bedrooms , @PathVariable  String location){

        return   houseByBedRoomAndLocationInfo.getAllHouseNoOfBedRoomsAndLocation( bedrooms, location );

    }


}
