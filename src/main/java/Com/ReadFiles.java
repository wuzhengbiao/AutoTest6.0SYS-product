package Com;

import org.apache.poi.hssf.record.PageBreakRecord;

import java.io.File;
import java.util.logging.StreamHandler;

/**
 * Created by admin on 2017/10/27.
 */
public class ReadFiles {
    //传入对应文件夹返回第N个文件的绝对路径
    public String GetFilePath(String FilePath,int Number){
        String path = FilePath;
        int i = Number;
        File f = new File(path);

        if (!f.exists()){
            System.out.println(path + " not exists");
            return "";
        }
        else{
            File fa[]= f.listFiles();
            return fa[i-1].getPath();
        }
    }

    //传入对应文件夹返回文件总数
    public int GetFileN(String FilePath){
        String path = FilePath;
        File f = new File(path);
        if (!f.exists()){
            System.out.println(path + " not exists");
            return 0;
        }
        else{
            int n = f.list().length;
            return n;
        }
    }
}
