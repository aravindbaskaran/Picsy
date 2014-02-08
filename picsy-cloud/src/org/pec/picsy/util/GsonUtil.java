package org.pec.picsy.util;

import org.pec.picsy.entity.PicsyPic;
import org.pec.picsy.entity.serialize.PicsyPicSerializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class GsonUtil {

	private static Gson GSON;
	static{
		GSON = new GsonBuilder()
			.serializeNulls()
			.registerTypeAdapter(PicsyPic.class, new PicsyPicSerializer())
			.create();
	}
	public static JsonElement serialize(Object obj){
		return GSON.toJsonTree(obj);
	}
}