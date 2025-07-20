package com.paymentchain.customer.common;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "This model is used to return errors in RFC 7807 format, which created generalized error-handling schema composed  by five parts")
@NoArgsConstructor
@Data
public class StandarizedApiExceptionResponse {

    @Schema(description = "The unique uri identifier that categorizes the error ", name="type",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-authorized")
    private String type;

    @Schema(description = "A brief, human-readable message about the error", name = "title",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "The user does not have authorization")
    private String title;

    @Schema(description = "The unique error code", name = "code",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "192")
    private String code;

    @Schema(description = "A human-readable explanation of the error", name = "detail",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "The user does not have the proper permissions to acces the"
            + " resource, please contact with Laura Escobar")
    private String detail;

    @Schema(description = "A URI that identifies the specific occurrence of the error", name = "detail",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-authorized/01")
    private String instance;
    //constructor
    public StandarizedApiExceptionResponse(String type, String title, String code, String detail) {
        super();
        this.title = title;
        this.code = code;
        this.detail = detail;
        this.type = type;
    }

}
