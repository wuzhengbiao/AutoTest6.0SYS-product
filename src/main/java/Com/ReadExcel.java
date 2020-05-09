package Com;

//import com.sun.org.apache.xerces.internal.xs.XSFacet;
//import junit.framework.TestCase;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.sl.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

//import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by win7 on 2016/1/3.
 * 本类都是对.xlsx的操作
 */
public class ReadExcel {
    /**
     * read the Excel file
     * @param path the path of the Excel file
     * @return
     * @throws java.io.IOException
     */
    public List<TestingCase> readExcel(String path) throws IOException {
        if (path == null || Common.EMPTY.equals(path)) {
            return null;
        } else {
//            String postfix = Util.getPostfix(path);
            String postfix = Util.getPostfix("xls/readXLSX.xlsx");
            if (!Common.EMPTY.equals(postfix)) {
                if (Common.OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
                    return readXls(path);
                } else if (Common.OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
                    return readXlsx(path);
                }
            } else {
                System.out.println(path + Common.NOT_EXCEL_FILE);
            }
        }
        return null;
    }

    /**
     * Read the Excel 2010
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public List<TestingCase> readXlsx(String path) throws IOException{
        System.out.println(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        TestingCase testingCase = null;
        List<TestingCase> list = new ArrayList<TestingCase>();
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum=1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    testingCase = new TestingCase();
                    XSSFCell no = xssfRow.getCell(0);
                    XSSFCell description = xssfRow.getCell(1);
                    XSSFCell model = xssfRow.getCell(2);
                    XSSFCell mode = xssfRow.getCell(3);
                    XSSFCell modepath = xssfRow.getCell(4);
                    XSSFCell text = xssfRow.getCell(5);
                    XSSFCell AppAuthentication = xssfRow.getCell(6);
                    XSSFCell whereskip = xssfRow.getCell(7);
                    String ReturnForm = ProcessingExcelDataTypes(text, getValue(text));//调用数据处理方法
                    testingCase.setId(getValue(no));
                    testingCase.setDescription(getValue(description));
                    testingCase.setModel(getValue(model));
                    testingCase.setMode(getValue(mode));
                    testingCase.setModePath(getValue(modepath));
                    testingCase.setText(ReturnForm);
                    try{
                        testingCase.setAppAuthentication(getValue(AppAuthentication));
                    }
                    catch (Exception e) {
                        testingCase.setAppAuthentication("空");
                    }
                    try{
                        testingCase.setWhetherskip(getValue(whereskip));
                    }
                    catch (Exception e) {
                        testingCase.setWhetherskip("否");
                    }

                    if(testingCase.getWhetherskip().equals("否"))//判断是否跳过执行步骤
                    {
                        list.add(testingCase);
                    }
                   }
            }
        }
        return list;
    }
    /**
     **处理读取excel数据类型，时间和数值等
     */
    public static String ProcessingExcelDataTypes(XSSFCell cell,String result) {
        switch(cell.getCellType()){
            case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                if (DateUtil.isCellDateFormatted(cell)) //是否是时间
                {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    result = dateFormat.format(date);
                }
                else {
                    DecimalFormat data = new DecimalFormat("0");//设置阿拉伯数字
                    result = data.format(cell.getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_STRING://字符串
                result=cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN://布尔值
                Boolean val1 = cell.getBooleanCellValue();
                result=val1.toString();
                break;
            case HSSFCell.CELL_TYPE_BLANK: // 空值
                System.out.println("BLANK \n");
                break;
            case HSSFCell.CELL_TYPE_FORMULA: // 公式
                System.out.println(cell.getCellFormula()+ "公式后续有用到在处理\n");
                break;
            case HSSFCell.CELL_TYPE_ERROR: // 故障
                System.out.println(cell.getErrorCellValue()+"\n");
                break;
            default:
                System.out.print("未知类型 \n");
                break;
        }
        return result;
    }

    /**
     * Read the Excel 2003-2007
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public List<TestingCase> readXls(String path) throws IOException {
        System.out.println(Common.PROCESSING + path);
        InputStream is = new FileInputStream(path);
//        Workbook workbook = WorkbookFactory.create(is);
//        Sheet hssSheet = (Sheet) workbook.getSheetAt(0);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        TestingCase testingCase = null;
        List<TestingCase> list = new ArrayList<TestingCase>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    testingCase = new TestingCase();
                    HSSFCell no = hssfRow.getCell(0);
                    HSSFCell description = hssfRow.getCell(1);
                    HSSFCell model = hssfRow.getCell(2);
                    HSSFCell mode = hssfRow.getCell(3);
                    HSSFCell modepath = hssfRow.getCell(4);
                    HSSFCell text = hssfRow.getCell(5);
                    testingCase.setId(getValue(no));
                    testingCase.setDescription(getValue(description));
                    testingCase.setModel(getValue(model));
                    testingCase.setMode(getValue(mode));
                    testingCase.setModePath(getValue(modepath));
                    testingCase.setText(getValue(text));
                    list.add(testingCase);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}