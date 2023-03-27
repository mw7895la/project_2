package shoppingMall.project_2.domain.item;

import lombok.Data;

@Data
public class UploadFile {
    private String uploadFileName;      //고객이 저장한 파일
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
