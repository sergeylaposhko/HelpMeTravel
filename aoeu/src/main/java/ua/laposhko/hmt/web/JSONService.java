package ua.laposhko.hmt.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import ua.laposhko.hmt.entity.Place;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/test")
public class JSONService {

    @Context
    ServletContext context;

    Logger log = Logger.getLogger(JSONService.class);


    @BadgerFish
    @GET
    @Path("/get")
    @Produces("application/json")
    public Map<String, String> getMovieInJSON() {
        File file = new File("");
        System.out.println("!!!" + file.getAbsolutePath());
        System.out.println("!!!" + context.getRealPath(""));

        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        return map;
    }

    @BadgerFish
    @GET
    @Path("/kitty")
    @Produces("application/json")
    public Response getKisa() {
        return Response.status(Response.Status.ACCEPTED).entity("I love you!!! You are on my web server now!=)").build();
    }

    @BadgerFish
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