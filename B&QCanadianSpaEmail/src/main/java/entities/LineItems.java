package entities;

import java.util.Arrays;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


public class LineItems {
	
	public Long id;
	public String[] productTitles;
	public int[] quantities;
	public LineItems(String[] productTitles, int[] quantities) {
		this.productTitles = productTitles;
		this.quantities = quantities;
	}
	public LineItems() {
		
	}
	@Override
	public String toString() {
		String output = "";
		for(int i = 0; i < productTitles.length; i ++ )
		{
			output += productTitles[i] + ": " + quantities[i] + ",\r\n";
		}
		return output;
	}
	
	
	
	
	
	
}
