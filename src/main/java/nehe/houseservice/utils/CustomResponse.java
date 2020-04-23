package nehe.houseservice.utils;

import nehe.houseservice.models.FailResponse;
import nehe.houseservice.models.SuccessResponse;

public class CustomResponse {

    public  static SuccessResponse successResponse(Object data , String message) {
        return new SuccessResponse("success",message, data);
    }

    public  static FailResponse failResponse( String message) {
        return new FailResponse("fail", message);
    }
}
