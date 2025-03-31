package vn.iuh.edu.fit.consult.models.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThreadRunRequest {
    @SerializedName(value="assistant_id")
    private String assistantId;

    @SerializedName(value="tool_choice")
    private Map<String, Object> tool_choice;
}
