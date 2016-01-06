package com.wetrack.fileupload;

import com.wetrack.fileupload.form.Base64ImageForm;

/**
 * Created by zhanghong on 16/1/4.
 */
public interface FileUploadService {

    /**
     *
     * @param form
     * @param path
     * @param fileName
     * @return 图片访问的url
     * @throws Exception
     */
    String uploadBase64Image(Base64ImageForm form, String path, String fileName) throws Exception;
}
