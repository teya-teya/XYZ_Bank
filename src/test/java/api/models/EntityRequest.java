package api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EntityRequest {

    @JsonProperty("title")
    private String title;

    @JsonProperty("verified")
    private Boolean verified;

    @JsonProperty("important_numbers")
    private List<Integer> important_numbers;

    @JsonProperty("addition")
    private AdditionRequest addition;

    @Data
    public static class AdditionRequest {
        @JsonProperty("additional_info")
        private String additional_info;

        @JsonProperty("additional_number")
        private Integer additional_number;
    }
}
