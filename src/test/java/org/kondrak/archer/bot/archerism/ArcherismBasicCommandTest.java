package org.kondrak.archer.bot.archerism;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kondrak.archer.bot.core.Context;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArcherismBasicCommandTest {

    @Mock
    private IMessage messageMock;

    @Mock
    private Context ctxMock;

//    @Mock
//    private ConfigurationService configServiceMock;

    @Mock
    private ArcherismDao dao;

    @Mock
    private IGuild guildMock;

    private ArcherismBasicCommand testee;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(ArcherismBasicCommandTest.class);
        testee = new ArcherismBasicCommand("!archerism", dao);
    }

    @After
    public void reset() {
        Mockito.reset(ctxMock, messageMock);
    }

    @Test
    public void shouldOnlyRespondToCommand() {
        when(dao.getArcherisms()).thenReturn(Arrays.asList(
                new Archerism("", "you found an archerism"),
                new Archerism("", "you found another archerism")
        ));
        when(messageMock.getGuild()).thenReturn(guildMock);
        when(messageMock.getContent()).thenReturn("!archerism");
//        when(configServiceMock.isConfiguredForGuild(any(IGuild.class), any(ConfigType.class)))
//                .thenReturn(true);

        if(testee.shouldExecute(messageMock)) {
            testee.execute(messageMock);
        }

        verify(messageMock, times(1)).reply(anyString());
        verify(messageMock, times(1)).getContent();
    }
}
