public class ConfigBird 
{
	
	processing.data.JSONObject allConfigs;
	
	public ConfigBird (String _config) {
		allConfigs = loadJSONObject(_config);
	}

	public String[] jsonArrayToStringArray(String _jsonArray) {
		processing.data.JSONArray tempJsonArray = allConfigs.getJSONArray(_jsonArray);

		String [] citations = new String[tempJsonArray.size()];
		for (int i = 0; i < tempJsonArray.size(); i++){
			citations[i] = tempJsonArray.getString(i);
		}
		return citations;
	}

	public String getString(String _value) {
		return allConfigs.getString(_value);
	}

	public boolean getBool(String _value) {
		return allConfigs.getBoolean(_value);
	}

	public int getInt(String _value) {
		return allConfigs.getInt(_value);
	}

	public String metersToKilometers(float _value){
		String valueString;

		if(_value > 1000){
			valueString = _value/1000+" km";
		}else{
			valueString = _value+" m";
		}
		return valueString;
	}

	public String makeCustomMessage(float _value){
		String[] messages = jsonArrayToStringArray("citations");
		String stringToReturn = "";
		int counter = 500;
		int palier = getInt("palier");
		int taille = messages.length;

		for (int i = 0; i<taille; i++){

			if(i == taille -1){
				stringToReturn = messages[taille -1];
			}else if(_value < counter && _value >counter - palier){
				stringToReturn =  messages[i];
			}
			counter += palier;
		}
		return stringToReturn;
	}

}
