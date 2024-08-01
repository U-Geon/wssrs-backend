package bootcamp.wssrs.domain.File.dto;


import bootcamp.wssrs.domain.File.entity.File;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileDTO {
    private String url;

    public FileDTO(File file) {
        this.url = file.getUrl();
    }
}
