package com.wetrack.fileupload.impl;

import com.wetrack.fileupload.FileUploadService;
import com.wetrack.fileupload.form.Base64ImageForm;
import com.wetrack.ikongtiao.image.ImageUtils;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/1/4.
 */
@Service
public class FileUploadServiceLocalStorageImpl implements FileUploadService {

    @Override
    public String uploadBase64Image(Base64ImageForm form, String path, String fileName) throws Exception {
        ImageUtils.saveBase64ImageToFile(path, fileName, form.getType(), form.getData(), form.getX(), form.getY()
                                            , form.getW(), form.getY(), form.getScale(), form.isCut());

        //TODO : 可配置化
        return "/images/" + fileName;
    }
}
