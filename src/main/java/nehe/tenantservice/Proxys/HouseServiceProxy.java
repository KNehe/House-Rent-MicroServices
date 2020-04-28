package nehe.tenantservice.Proxys;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//@FeignClient(name = "house-service", url = "localhost:8001/api/v1/house-service")
@FeignClient(name = "house-service")
@RibbonClient(name = "house-service")
public interface HouseServiceProxy {

    String url_prefix = "/api/v1/house-service";

    @GetMapping( url_prefix + "/location/{location}")
    ResponseEntity<?> getHouseByLocation(@PathVariable String location);

    @GetMapping( url_prefix + "/house/{id}")
    ResponseEntity<?> getHouseById(@PathVariable Long id);

    @GetMapping( url_prefix + "/bedrooms/{bedrooms}")
    ResponseEntity<?> getHouseByNoOfBedrooms(@PathVariable int bedrooms);

    @GetMapping( url_prefix + "/total-rooms/{totalrooms}")
    ResponseEntity<?> getHouseByTotalNoOfRooms(@PathVariable int totalrooms);

    @GetMapping( url_prefix + "/bedrooms/{bedrooms}/location/{location}")
    ResponseEntity<?> getAllHouseNoOfBedRoomsAndLocation(@PathVariable int bedrooms , @PathVariable String location);

    @GetMapping(url_prefix +"/page/{page}/size/{size}")
    ResponseEntity<?>  getAllHouses(@PathVariable int page, @PathVariable int size);
}
