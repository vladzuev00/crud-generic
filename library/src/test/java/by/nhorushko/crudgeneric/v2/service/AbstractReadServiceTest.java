package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.domain.dto.Message;
import by.nhorushko.crudgeneric.domain.dto.Message.GpsCoordinate;
import by.nhorushko.crudgeneric.domain.entity.MessageEntity;
import by.nhorushko.crudgeneric.v2.mapper.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class AbstractReadServiceTest {

    @Mock
    private Mapper<MessageEntity, Message> mockedMapper;

    @Mock
    private JpaRepository<MessageEntity, Long> mockedRepository;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Captor
    private ArgumentCaptor<MessageEntity> messageEntityArgumentCaptor;

    private MessageReadService service;

    @Before
    public void initializeService() {
        this.service = new MessageReadService(this.mockedMapper, this.mockedRepository);
    }

    @Test
    public void messageShouldBeFoundById() {
        final Long givenId = 255L;
        final MessageEntity foundEntity = new MessageEntity(givenId, 5.5F, 4.6F, 10, 15,
                20);
        when(this.mockedRepository.findById(anyLong())).thenReturn(Optional.of(foundEntity));

        final Message expected = new Message(givenId, new GpsCoordinate(5.5F, 4.6F), 10, 15,
                20);
        when(this.mockedMapper.toDto(any(MessageEntity.class))).thenReturn(expected);

        final Optional<Message> optionalActual = this.service.getById(givenId);
        assertTrue(optionalActual.isPresent());
        final Message actual = optionalActual.get();
        assertEquals(expected, actual);

        verify(this.mockedRepository, times(1)).findById(this.longArgumentCaptor.capture());
        verify(this.mockedMapper, times(1)).toDto(this.messageEntityArgumentCaptor.capture());

        assertSame(givenId, this.longArgumentCaptor.getValue());
        assertSame(foundEntity, this.messageEntityArgumentCaptor.getValue());
    }

    @Test
    public void messageShouldNotBeFoundById() {
        final Long givenId = 255L;
        when(this.mockedRepository.findById(anyLong())).thenReturn(empty());

        final Optional<Message> optionalActual = this.service.getById(givenId);
        assertTrue(optionalActual.isEmpty());

        verify(this.mockedRepository, times(1)).findById(this.longArgumentCaptor.capture());
        verify(this.mockedMapper, times(0)).toDto(any(MessageEntity.class));

        assertSame(givenId, this.longArgumentCaptor.getValue());
    }

    @SuppressWarnings("all")
    private static final class MessageReadService
            extends AbstractReadService<Long, MessageEntity, Message, Mapper<MessageEntity, Message>> {

        public MessageReadService(Mapper<MessageEntity, Message> mapper,
                                  JpaRepository<MessageEntity, Long> repository) {
            super(mapper, repository);
        }
    }
}
