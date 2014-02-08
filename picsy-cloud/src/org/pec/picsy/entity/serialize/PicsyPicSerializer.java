package org.pec.picsy.entity.serialize;

import java.lang.reflect.Type;

import org.pec.picsy.entity.PicsyPic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PicsyPicSerializer implements JsonSerializer<PicsyPic> {
	@Override
	public JsonElement serialize(PicsyPic pic, Type arg1,
			JsonSerializationContext arg2) {
		JsonObject r = new JsonObject();
		r.addProperty("photo", pic.getServingUrl());
		r.addProperty("key", pic.getKey().getId());
		r.addProperty("desc", pic.getDesc());
		r.addProperty("by", pic.getBy());
		r.addProperty("createdOn", pic.getCreatedOn());
		return r;
	}
}
