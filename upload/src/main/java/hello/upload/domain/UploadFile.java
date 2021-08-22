package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    //고객이 업로드한 파일명이 서버의 기존 파일 이름과 충돌이 날 수 있기 때문에, 서버에서 파일명이 겹치지않도록 내부에서 관리하는 파일명이 별도로 필요하다.
    private String uploadFileName; //고객이 업로드판 파일명
    private String storeFileName; //서버 내부에서 관리하는 파일명

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
