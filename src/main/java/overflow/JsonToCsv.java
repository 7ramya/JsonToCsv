package overflow;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonToCsv {

	public static void main(String args[]) throws JSONException, IOException
	{
		List<JSONObject> infoList=new ArrayList<JSONObject>();
		JSONObject info=new JSONObject();
		JSONObject name=new JSONObject();
		name.put("first", "John");
		name.put("last","Doe");
		info.put("name",name );

		List<JSONObject> itemList=new ArrayList<JSONObject>();
		JSONObject item1=new JSONObject();
		JSONObject item2=new JSONObject();
		item1.put("item1", "val1");
		itemList.add(item1);
		item2.put("item2", "val2");
		itemList.add(item2);


		info.put("item", itemList);
		infoList.add(info);
		
        System.out.println(infoList);
		convertJsonToCsv(infoList);

	}

	public static void convertJsonToCsv(List<JSONObject> infoList) throws IOException, JSONException {

		FileWriter csvWriter = new FileWriter("InfoFile.csv"); 
		List<String> header= new ArrayList<String>();
		List<String> values = new ArrayList<String>();

		for(int i=0;i<infoList.size();i++) {

			JSONObject info_obj=infoList.get(i);
			Iterator<String> info_keys=info_obj.keys();
			while(info_keys.hasNext()) {
				String key = info_keys.next();

				if (info_obj.get(key) instanceof JSONObject) {

					JSONObject obj=info_obj.getJSONObject(key);
					Iterator<String> obj_keys=obj.keys();
					while(obj_keys.hasNext()) {
						String k=obj_keys.next();
						if(i==0)
							header.add(k);
						values.add(obj.getString(k));
					}

				}
				if (info_obj.get(key) instanceof JSONArray) {


					JSONArray item_array=info_obj.getJSONArray(key);
					for(int j=0;j<item_array.length();j++) {

						JSONObject item=item_array.getJSONObject(j);
						Iterator<String> item_keys=item.keys();
						while(item_keys.hasNext()) {
							String k=item_keys.next();
							if(i==0)
								header.add(k);
							values.add(item.getString(k));
						}
					}

				}

			}


		}
		
		for(String head : header) {

			csvWriter.append(head);  
			csvWriter.append(","); 
		}
		csvWriter.append("\n");

		for(int m=0;m<values.size();m++) {

			
			csvWriter.append(values.get(m)); 
			if((m+1)%(header.size())==0) {
				
				csvWriter.append("\n"); 
			}
			else {
				csvWriter.append(","); 
			}

		}
		csvWriter.flush();


	}
}