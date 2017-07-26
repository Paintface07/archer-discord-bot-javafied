package org.kondrak.archer.bot;

import org.kondrak.archer.bot.core.ArcherBotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 11/3/2016.
 */
public class ArcherMain {

    private static final Logger LOG = LoggerFactory.getLogger(ArcherMain.class);

    public static void main(String[] args) {
        FileInputStream fs = null;
        try {
            fs = new FileInputStream("config.properties");
            Properties props = new Properties();
            props.load(fs);
            props.list(System.out);
            new ArcherBotContext(props);
        } catch (IOException ex) {
            LOG.error("Could not open properties file.");
        } finally {
            try {
                if(null != fs) fs.close();
            } catch (IOException ex) {
                LOG.error("Could not close properties file.");
            }
        }
    }
}
