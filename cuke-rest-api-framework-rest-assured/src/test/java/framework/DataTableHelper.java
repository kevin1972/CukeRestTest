package framework;

import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;

public class DataTableHelper {
	
	
	
	public static void displayTableValues(DataTable dt, Boolean hasHeader) {
		List<List<String>> list = dt.asLists(String.class);		
		int startIndex = hasHeader == true ? 1 : 0; 
		for(int i = startIndex; i<list.size(); i++) { //i starts from 1 because i=0 represents the header
			System.out.println(list.get(i).get(0)); 
			System.out.println(list.get(i).get(1));
		}
	}
	
	
	public static String getValueFromTable(DataTable dt, int rowIndex, int columnIndex, Boolean hasHeader) {
		List<List<String>> table = dt.asLists(String.class);		
		String returnVal;
		if(hasHeader) {
			returnVal = table.get(rowIndex-1).get(columnIndex);
		}else {
			returnVal = table.get(rowIndex).get(columnIndex);
		}		
		return returnVal;	
	}
	
	
	
	

}
