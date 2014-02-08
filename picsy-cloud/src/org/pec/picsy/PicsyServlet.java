package org.pec.picsy;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.pec.picsy.entity.PicsyPic;
import org.pec.picsy.util.GsonUtil;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.gson.JsonElement;

@SuppressWarnings({ "serial", "deprecation" })
public class PicsyServlet extends HttpServlet {
	
	private Logger log = Logger.getLogger(PicsyServlet.class.getName());
	private static ImagesService imagesService = ImagesServiceFactory.getImagesService();
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// TODO Return photos list
		EntityManager em = EMF.get().createEntityManager();
		Query q = em.createQuery("SELECT A FROM " + PicsyPic.class.getName() + " A ORDER BY A.createdOn DESC");
		@SuppressWarnings("unchecked")
		List<PicsyPic> results = (List<PicsyPic>)q.getResultList();
		em.close();
		resp.getWriter().print(GsonUtil.serialize(results));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload();
	    FileItemIterator iterator;
	    PicsyPic pic = null;
    	try {
    		HashMap<String, Object> params = new HashMap<String, Object>();
    		String servingUrl = null;
    		iterator = upload.getItemIterator(req);
    		while (iterator.hasNext()) {
    			FileItemStream item = iterator.next();
    			InputStream stream = item.openStream();
    			String fieldName = item.getFieldName();
    			log.info("Field found - " + fieldName); 
    			if (!item.isFormField()) {
    				byte[] content = IOUtils.toByteArray(stream);
    				BlobKey fileKey;
    				FileService fileService = FileServiceFactory.getFileService();
    				AppEngineFile file = fileService.createNewBlobFile("image/png");
    				FileWriteChannel writeChannel = fileService.openWriteChannel(file, true);
    				BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(content));
    				byte[] bytes = new byte[1024];
    				int bytesLen = in.read(bytes);
    				while (bytesLen >= 0){
    					writeChannel.write(
    						java.nio.ByteBuffer.wrap(bytes, 0, bytesLen));
    					bytesLen = in.read(bytes);
    				}
    				writeChannel.closeFinally();
    				fileKey = fileService.getBlobKey(file);
    				servingUrl = imagesService.getServingUrl(
    					ServingUrlOptions.Builder.withBlobKey(fileKey));
    				params.put(fieldName, fileKey);
    			  }
    			else {
    				String fieldValue = Streams.asString(stream, "UTF-8");
    				params.put(fieldName, fieldValue);
    			}
    		}
		    pic = new PicsyPic((BlobKey)params.get("photo"),(String)params.get("desc"), (String)params.get("by"));
		    pic.setServingUrl(servingUrl);
		    // persist image
		    EntityManager em = EMF.get().createEntityManager();
		    try{
		    	em.persist(pic);
		    }finally{
		    	em.close();
		    }
		    JsonElement responseJson = GsonUtil.serialize(pic);
		    resp.getWriter().print(responseJson);
		} catch (FileUploadException e) {
			log.severe("Very bad request");
			e.printStackTrace();
		}
    	
	}
}
