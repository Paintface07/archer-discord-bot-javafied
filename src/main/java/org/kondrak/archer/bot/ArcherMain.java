package org.kondrak.archer.bot;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.kondrak.archer.bot.configuration.ConfigDao;
import org.kondrak.archer.bot.configuration.ConfigurationService;
import org.kondrak.archer.bot.core.Bot;
import org.kondrak.archer.bot.core.listener.ChannelListener;
import org.kondrak.archer.bot.core.listener.MessageListener;
import org.kondrak.archer.bot.core.listener.ModuleListener;
import org.kondrak.archer.bot.core.listener.ReadyListener;
import org.kondrak.archer.bot.core.listener.UserListener;
import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

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
//            props.list(System.out);

            SqlSessionFactory factory = configureMybatis(props.getProperty("db.server"),
                    props.getProperty("db.user"), props.getProperty("db.password"),
                    props.getProperty("db.name"));

            Bot.getInstance(props, setupClient(props.getProperty("discord.token")), factory, new ConfigurationService(new ConfigDao(factory)))
                    .builder()
                        .withListeners(
                                new UserListener(),
                                new ReadyListener(),
                                new ModuleListener(),
                                new MessageListener(),
                                new ChannelListener())
                    .context()
                        .withCommands("archerism", "word", "roll", "admin load", "config", "help", "timer");

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

    private static IDiscordClient setupClient(String appKey) {
        try {
            return buildClient(appKey, true);
        } catch(DiscordException dx) {
            LOG.error("Could not create discord client!", dx);
            return null;
        }
    }

    private static IDiscordClient buildClient(String token, boolean login) {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        LOG.info("Attempting login with: {}", token);

        if (login) {
            return clientBuilder.login();
        } else {
            return clientBuilder.build();
        }
    }

    private static SqlSessionFactory configureMybatis(String server, String user, String password, String database) {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName(database);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setServerName(server);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("org.kondrak.archer.bot");
        return new SqlSessionFactoryBuilder().build(configuration);
    }
}
