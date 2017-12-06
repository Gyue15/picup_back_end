package cn.edu.nju.gyue.util;

import cn.edu.nju.gyue.configuration.FilePathConfig;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class FileUtil {

    public static Map<String, String> filePathMap;

    public static void upLoad(MultipartFile file) {
        if (filePathMap == null) {
            filePathMap = new TreeMap<>();
        }
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                String filePath = FilePathConfig.PATH + System.currentTimeMillis() + file.getOriginalFilename();
                // 文件url
                String fileUrl = FilePathConfig.URL + System.currentTimeMillis() + file.getOriginalFilename();
                File dest = new File(filePath);

                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }

                file.transferTo(dest);
                filePathMap.put(file.getOriginalFilename(), fileUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("upload success");
    }
}
