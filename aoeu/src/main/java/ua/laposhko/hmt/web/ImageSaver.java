package ua.laposhko.hmt.web;

import java.io.File;
import java.io.InputStream;

import org.apache.log4j.Logger;

import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.Place;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.filesystem.FileSaver;

// TODO: Auto-generated Javadoc

/**
 * The Class ImageSaver.
 *
 * @author Sergey Laposhko
 */
public class ImageSaver {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(ImageSaver.class);

    /**
     * The image folder.
     */
    private static String imageFolder;

    /**
     * The Constant IMAGE_FOLDER.
     */
    private static final String IMAGE_FOLDER = "img";

    /**
     * The Constant PLACE_IMAGE_FOLDER.
     */
    private static final String PLACE_IMAGE_FOLDER = IMAGE_FOLDER + File.separator + "places";

    private static final String CITY_IMAGE_FOLDER = IMAGE_FOLDER + File.separator + "cities";

    private static final String USER_IMAGE_FOLDER = IMAGE_FOLDER + File.separator + "users";

    /**
     * The Constant DEF_PLACE_IMG.
     */
    public static final String DEF_PLACE_IMG = PLACE_IMAGE_FOLDER + File.separator + "def.jpeg";

    public static final String DEF_CITY_IMG = CITY_IMAGE_FOLDER + File.separator + "def.jpeg";

    public static final String DEF_USER_PHOTO = USER_IMAGE_FOLDER + File.separator + "def.jpeg";

    /**
     * Save place image.
     *
     * @param inputStream the input stream
     * @param place       the place
     * @param fileName    the file name
     * @return the string
     */
    public static final String savePlaceImage(InputStream inputStream,
                                              Place place, String fileName) {
        String extention = getExtension(fileName);
        String location = PLACE_IMAGE_FOLDER + File.separator
                + place.getId() + "." + extention;
        FileSaver.saveFile(inputStream, imageFolder + location);
        LOGGER.debug("Saving image with real path: " + imageFolder + location);
        LOGGER.debug("Returning location: " + location);
        return location;
    }

    /**
     * Save city image.
     *
     * @param uploadedInputStream the uploaded input stream
     * @param city                the city
     * @param fileName            the file name
     * @return the string
     */
    public static String saveCityImage(InputStream inputStream,
                                       City city, String fileName) {
        String extention = getExtension(fileName);
        String location = CITY_IMAGE_FOLDER + File.separator
                + city.getId() + "." + extention;
        FileSaver.saveFile(inputStream, imageFolder + location);
        LOGGER.debug("Saving image with real path: " + imageFolder + location);
        LOGGER.debug("Returning location: " + location);
        return location;
    }

    /**
     * @param inputStream
     * @param newUser
     * @param fileName
     */
    public static String saveUserPhoto(InputStream inputStream,
                                       User newUser, String fileName) {
        String extention = getExtension(fileName);
        String location = USER_IMAGE_FOLDER + File.separator
                + newUser.getId() + "." + extention;
        FileSaver.saveFile(inputStream, imageFolder + location);
        LOGGER.debug("Saving image with real path: " + imageFolder + location);
        LOGGER.debug("Returning location: " + location);
        return location;
    }

    /**
     * Sets the image folder real path.
     *
     * @param realPath the new image folder real path
     */
    public static void setImageFolderRealPath(String realPath) {
        imageFolder = realPath.replace("img" + File.separator, "");
        LOGGER.debug("Image folder is set to " + imageFolder);
    }

    /**
     * Gets the extension.
     *
     * @param fileName the file name
     * @return the extension
     */
    private static String getExtension(String fileName) {
        return fileName.split("\\.")[1];
    }


}
