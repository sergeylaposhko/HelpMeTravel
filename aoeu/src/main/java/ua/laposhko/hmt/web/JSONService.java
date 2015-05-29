package ua.laposhko.hmt.web;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.log4j.Logger;
import ua.laposhko.hmt.entity.Place;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Path("/test")
public class JSONService {

    Logger log = Logger.getLogger(JSONService.class);


    @GET
    @Path("/get")
    @Produces("application/json")
    public Map<String, String> getMovieInJSON() {
        File file = new File("");
        System.out.println("!!!" + file.getAbsolutePath());

        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        return map;
    }

    @GET
    @Path("/kitty")
    @Produces("application/json")
    public Response getKisa() {
        return Response.status(Response.Status.ACCEPTED).entity("I love you!!! You are on my web server now!=)").build();
    }

    @GET
    @Path("/cook")
    @Produces("application/json")
    public void testCokies(@QueryParam("cook") String cook) {
        System.out.println(cook);
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail,
            @FormDataParam("text") String text) {
        Place place = new Place();
        System.out.println(fileDetail.getFileName());
        System.out.println(text);
        place.setId(10);
        ImageSaver.savePlaceImage(uploadedInputStream, place, fileDetail.getFileName());

//	String uploadedFileLocation = fileDetail.getFileName();
//
//	// save it
//	writeToFile(uploadedInputStream, uploadedFileLocation);
//
//	String output = "File uploaded to : " + uploadedFileLocation;

        return Response.status(200).entity("").build();
    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(
                    uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}