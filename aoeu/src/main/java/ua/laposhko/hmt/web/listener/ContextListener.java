package ua.laposhko.hmt.web.listener;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import ua.laposhko.hmt.web.ImageSaver;

/**
 * @author Sergey Laposhko
 */
public class ContextListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        LOGGER.info("Context destroing.");
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        setUpPhotoBaseDir(arg0.getServletContext());

        try {
            System.out.println("Sleeping");
            Thread.sleep(1000);
            System.out.println("Awake");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param servletContext
     */
    private void setUpPhotoBaseDir(ServletContext servletContext) {
        String imagePath = servletContext.getRealPath("img" + File.separator);
        ImageSaver.setImageFolderRealPath(imagePath + File.separator);
        LOGGER.info("image is set to " + imagePath + File.separator);
    }

}
