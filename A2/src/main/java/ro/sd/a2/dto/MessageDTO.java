package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageDTO {

    private String message;

    @Override
    public String toString() {
        return message;
    }
}
