package nehe.tenantservice.utils;


import nehe.tenantservice.models.SuccessResponse;

public class CustomResponse {

    public  static SuccessResponse successResponse(Object data , String message) {
        return new SuccessResponse("Not found",message, data);
    }

}
