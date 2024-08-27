package bootcamp.wssrs.domain.File.dto;


import bootcamp.wssrs.domain.File.entity.File;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileDTO {
    @NotBlank(message = "URL이 존재하지 않습니다.")
    private String url;

    public FileDTO(File file) {
        this.url = file.getUrl();
    }
}
