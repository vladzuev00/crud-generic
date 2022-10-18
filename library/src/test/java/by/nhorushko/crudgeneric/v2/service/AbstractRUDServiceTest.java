package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.domain.dto.Message;
import by.nhorushko.crudgeneric.domain.dto.Message.GpsCoordinate;
import by.nhorushko.crudgeneric.domain.entity.MessageEntity;
import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.v2.mapper.AbstractMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class AbstractRUDServiceTest {

    @Mock
    private AbstractMapper<MessageEntity, Message> mockedMapper;

    @Mock
    private JpaRepository<MessageEntity, Long> mockedRepository;

    @Captor
    private ArgumentCaptor<Message> messageArgumentCaptor;

    @Captor
    private ArgumentCaptor<MessageEntity> messageEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    private MessageRUDService service;

    @Before
    public void initializeService() {
        this.service = new MessageRUDService(this.mockedMapper, this.mockedRepository);
    }

    @Test
    public void messageShouldBeUpdated() {
        final Message givenMessageDtoToUpdate = new Message(255L, new GpsCoordinate(5.5F, 6.6F),
                10, 15, 20);

        final MessageEntity givenMessageEntityToUpdate = new MessageEntity(255L, 5.5F, 6.6F,
                10, 15, 20);
        when(this.mockedMapper.toEntity(any(Message.class))).thenReturn(givenMessageEntityToUpdate);

        final MessageEntity givenUpdatedMessageEntity = new MessageEntity(255L, 5.5F, 6.6F,
                10, 15, 20);
        when(this.mockedRepository.save(any(MessageEntity.class))).thenReturn(givenUpdatedMessageEntity);

        final Message givenUpdatedMessageDto = new Message(255L, new GpsCoordinate(5.5F, 6.6F),
                10, 15, 20);
        when(this.mockedMapper.toDto(any(MessageEntity.class))).thenReturn(givenUpdatedMessageDto);

        final Message actual = this.service.update(givenMessageDtoToUpdate);
        assertSame(givenUpdatedMessageDto, actual);

        verify(this.mockedMapper, times(1)).toEntity(this.messageArgumentCaptor.capture());
        verify(this.mockedRepository, times(1)).save(this.messageEntityArgumentCaptor.capture());
        verify(this.mockedMapper, times(1)).toDto(this.messageEntityArgumentCaptor.capture());

        assertSame(givenMessageDtoToUpdate, this.messageArgumentCaptor.getValue());

        final List<MessageEntity> expectedCapturedMessageEntityArguments
                = List.of(givenMessageEntityToUpdate, givenUpdatedMessageEntity);
        assertEquals(expectedCapturedMessageEntityArguments, this.messageEntityArgumentCaptor.getAllValues());
    }

    @Test(expected = IllegalArgumentException.class)
    public void messageShouldNotBeUpdatedBecauseOfIdIsNull() {
        final Message givenMessageDtoToUpdate = new Message(null, new GpsCoordinate(5.5F, 6.6F),
                10, 15, 20);
        this.service.update(givenMessageDtoToUpdate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void messageShouldNotBeUpdatedBecauseOfIdIsZero() {
        final Message givenMessageDtoToUpdate = new Message(0L, new GpsCoordinate(5.5F, 6.6F),
                10, 15, 20);
        this.service.update(givenMessageDtoToUpdate);
    }

    @Test
    public void messageShouldBePartialUpdated() {
        final Long givenId = 255L;
        final GpsCoordinate givenNewGpsCoordinate = new GpsCoordinate(5.5F, 6.5F);

        final MessageEntity givenMessageEntityToUpdate = new MessageEntity(255L, 3.3F, 4.4F,
                10, 15, 20);
        when(this.mockedRepository.findById(anyLong())).thenReturn(Optional.of(givenMessageEntityToUpdate));

        final MessageEntity givenUpdatedMessageEntity = new MessageEntity(255L, 5.5F, 6.5F,
                10, 15, 20);

        final Message givenUpdatedMessageDto = new Message(255L, new GpsCoordinate(5.5F, 6.5F),
                10, 15, 20);
        when(this.mockedMapper.toDto(any(MessageEntity.class))).thenReturn(givenUpdatedMessageDto);

        final Message actual = this.service.updatePartial(givenId, givenNewGpsCoordinate);
        assertSame(givenUpdatedMessageDto, actual);

        verify(this.mockedRepository, times(1)).findById(this.longArgumentCaptor.capture());
        verify(this.mockedMapper, times(1)).toDto(this.messageEntityArgumentCaptor.capture());

        assertSame(givenId, this.longArgumentCaptor.getValue());
        assertEquals(givenUpdatedMessageEntity, this.messageEntityArgumentCaptor.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void messageShouldNotBePartialUpdatedBecauseOfIdIsNull() {
        final GpsCoordinate givenNewGpsCoordinate = new GpsCoordinate(5.5F, 6.5F);
        this.service.updatePartial(null, givenNewGpsCoordinate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void messageShouldNotBePartialUpdatedBecauseOfIdIsZero() {
        final Long givenId = 0L;
        final GpsCoordinate givenNewGpsCoordinate = new GpsCoordinate(5.5F, 6.5F);

        this.service.updatePartial(givenId, givenNewGpsCoordinate);
    }

    @Test(expected = AppNotFoundException.class)
    public void messageShouldNotBePartialUpdatedBecauseOfNotExistingMessageWithGivenId() {
        final Long givenId = 255L;
        final GpsCoordinate givenNewGpsCoordinate = new GpsCoordinate(5.5F, 6.5F);

        when(this.mockedRepository.findById(anyLong())).thenReturn(empty());

        this.service.updatePartial(givenId, givenNewGpsCoordinate);
    }

    @Test
    public void messageShouldBeDeletedById() {
        final Long givenId = 255L;

        this.service.delete(givenId);

        verify(this.mockedRepository, times(1)).deleteById(this.longArgumentCaptor.capture());
        assertSame(givenId, this.longArgumentCaptor.getValue());
    }

    @SuppressWarnings("all")
    private static final class MessageRUDService
            extends AbstractRUDService<Long, MessageEntity, Message, AbstractMapper<MessageEntity, Message>, JpaRepository<MessageEntity, Long>> {

        public MessageRUDService(AbstractMapper<MessageEntity, Message> mapper,
                                 JpaRepository<MessageEntity, Long> repository) {
            super(mapper, repository);
        }
    }
}
