
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class InsertCSV {

        private String pathHorarios = "horariosOutput";
        private String pathXls = "horariosXls";

	private Properties properties;
        private String horarioFile;
	private Map<String, ArrayList<String>> clases;
	private Map<Integer, HSSFRow> filas;
	private int numClases;
	private int rowIndex;
	
	public InsertCSV(String file){
		this.horarioFile = file;
		this.rowIndex = 3;
                loadHorario();
		setNumClases();
		clases = new TreeMap<String, ArrayList<String>>();
		filas = new TreeMap<Integer, HSSFRow>();
	}
        private void loadHorario(){
                properties = new Properties();
		try {
                    properties.load(new FileInputStream(pathHorarios + "/" + horarioFile + ".properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
	
	private void fillFilas(HSSFSheet worksheet){
		for(int i = 10; i < 41; i++){
			filas.put(i, worksheet.createRow(i));
		}
	}
	
	
	private void setNumClases(){
		this.numClases = Integer.parseInt(properties.getProperty("NUMCLASES"));
	}
	
	public void run(){
		fillClases();
		fillXls();
	}
	
	private void fillClases(){
		String dias = "";
		String horaInicio = "";
		for(int i = 1;i <= numClases; i++){
			horaInicio = properties.getProperty(i + ".HORARIO");
			if(!clases.containsKey(horaInicio)){
				clases.put(horaInicio, new ArrayList<String>());
				clases.get(horaInicio).add(properties.getProperty(i + ".DIAS") + " "+properties.getProperty(i + ".DURACION") + " "+properties.getProperty(i + ".CLASE"));
			} else {
				clases.get(horaInicio).add(properties.getProperty(i + ".DIAS") + " "+properties.getProperty(i + ".DURACION") + " "+properties.getProperty(i + ".CLASE"));
			}
				
		}
		System.out.println("Horas: " + clases);
	}
	
	public void fillXls(){
		try{
			FileOutputStream fileOut = new FileOutputStream(pathXls + "/" + horarioFile + ".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet("HORARIO");
	
			fillHeader(worksheet, workbook);
			System.out.println("Empezando con el horario");
			fillSchedule(worksheet, workbook);
			fillTeacher(worksheet, workbook);
			System.out.println("Terminado");
			
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void fillTeacher(HSSFSheet worksheet, HSSFWorkbook workbook){
		String[] classData = {"CLASE", "MATERIA", "PROF", "DIAS", "HORARIO", "SALON"};
	//try{
		int r = 11;
		for(int j = 0; j < numClases; j++){
			
			for(int i = 0; i < classData.length; i++){
				HSSFRow row = filas.get(r);
				
				HSSFCell cell = row.createCell(9);
				cell.setCellValue(classData[i]+ ": " + properties.getProperty((j+1) + "." + classData[i]));
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(cellStyle);
				r++;
			}
			r++;
		}
		
	}
	
	private void fillSchedule(HSSFSheet worksheet, HSSFWorkbook workbook){
		System.out.println("Inicia Fill");
		Map<String, Integer> weekDays = new TreeMap<String, Integer>();
		weekDays.put("L", 1);
		weekDays.put("M", 2);
		weekDays.put("W", 3);
		weekDays.put("J", 4);
		weekDays.put("V", 5);
		weekDays.put("S", 6);

		//Iterator<String> it = clases.keySet().iterator();
		
		double counter = 7;
		boolean halfHour = false;
		int numFila = 0;
		System.out.println("RUM: " + rowIndex);
		fillFilas(worksheet);
		while(counter <= 22){
			int celda = rowIndex;
			String hora = "";
			//filas.put(rowIndex, worksheet.createRow(rowIndex));
			HSSFRow row2 = filas.get(rowIndex++);
			HSSFCell cell = row2.createCell(0);
			if (halfHour){
				hora = Double.toString(counter).substring(0, Double.toString(counter).length()-2) + ":30";
				cell.setCellValue(hora);
				halfHour = false;
			} else {
				hora = Double.toString(counter).substring(0, Double.toString(counter).length()-2) + ":00";
				cell.setCellValue(hora);
				halfHour = true;
			}
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cell.setCellStyle(cellStyle);
			counter += .5;
			System.out.println("Hora: " + hora+": " + clases.containsKey(hora));
			
			if (clases.containsKey(hora)){
				for(int i = 0; i < clases.get(hora).size() ; i++){
					System.out.println("i: " + i);
					String[] tempo = clases.get(hora).get(i).split(" ");
					System.out.println(tempo.length);
					for(int j = 0; j < tempo.length-2; j++){
						System.out.println("j: " + j);
						System.out.println("week: " +  weekDays.get(tempo[j]));
						
						HSSFCell cellN = row2.createCell(weekDays.get(tempo[j]));
						cellN.setCellValue(tempo[tempo.length-1]);
						HSSFCellStyle cellStyleN = workbook.createCellStyle();
						cellStyleN.setFillForegroundColor(HSSFColor.WHITE.index);
						cellStyleN.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						cellN.setCellStyle(cellStyleN);
						
						for(int k = 0; k < Integer.parseInt(tempo[tempo.length - 2]); k++){
							
							HSSFCell cellM = filas.get(celda + k).createCell(weekDays.get(tempo[j]));
							cellM.setCellValue(tempo[tempo.length-1]);
							HSSFCellStyle cellStyleM = workbook.createCellStyle();
							cellStyleN.setFillForegroundColor(HSSFColor.WHITE.index);
							cellStyleN.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							cellN.setCellStyle(cellStyleN);
							
						}
						
						
					}
					
				}
			}
			
			numFila++;
		}
			
		System.out.println("TErmina Fill");

	}
	
	@SuppressWarnings("deprecation")
	private void fillHeader(HSSFSheet worksheet, HSSFWorkbook workbook){
		
			String[] headerData = {"ALUMNO", "MATRICULA", "CARRERA", "UNIDADES", "PERIODO"};
			String[] weekDays = {"", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB"};
		//try{
			
			for(int i = 0; i < headerData.length; i++){
				HSSFRow row = worksheet.createRow(rowIndex++);
				
				HSSFCell cell = row.createCell( 0);
				cell.setCellValue(headerData[i]+ ": " + properties.getProperty(headerData[i]));
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(cellStyle);
			}
			rowIndex++;
			HSSFRow row = worksheet.createRow(rowIndex++);
			
			for (int i = 0; i < weekDays.length; i++){	
				
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(weekDays[i]);
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
				cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(cellStyle);
			}
			



			/*
			HSSFRow row1 = worksheet.createRow(row++);
			
			HSSFCell cellA1 = row1.createCell( 0);
			cellA1.setCellValue("ALUMNO: " + properties.getProperty("NOMBRE"));
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellA1.setCellStyle(cellStyle);
			
			HSSFRow row2 = worksheet.createRow(row++);

			HSSFCell cellB1 = row2.createCell(0);
			cellB1.setCellValue("MATRICULA: " + properties.getProperty("MATRICULA"));
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellB1.setCellStyle(cellStyle);
	
			HSSFRow row3 = worksheet.createRow(row++);

			HSSFCell cellC1 = row3.createCell(0);
			cellC1.setCellValue("CARRERA: " + properties.getProperty("CARRERA"));
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellC1.setCellStyle(cellStyle);
			
			
			HSSFRow row4 = worksheet.createRow(row++);

			HSSFCell cellD1 = row4.createCell(0);
			cellD1.setCellValue("UNIDADES: " + properties.getProperty("UNIDADES"));
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellD1.setCellStyle(cellStyle);
			
			HSSFRow row5 = worksheet.createRow(row++);

			HSSFCell cellE1 = row5.createCell(0);
			cellE1.setCellValue("PERIODO: " + properties.getProperty("PERIODO"));
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellE1.setCellStyle(cellStyle);
			*/
			
			
	/*		
			HSSFCell cellC1 = row2.createCell(2);
			cellC1.setCellValue(true);
	
			HSSFCell cellD1 = row2.createCell((short) 3);
			cellD1.setCellValue(new Date());
			cellStyle = workbook.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat
					.getBuiltinFormat("m/d/yy h:mm"));
			cellD1.setCellStyle(cellStyle);
	

		} catch (Exception e) {
			e.printStackTrace();
		} */
	}

}
