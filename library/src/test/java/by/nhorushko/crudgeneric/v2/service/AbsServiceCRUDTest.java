package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.domain.dto.Message;
import by.nhorushko.crudgeneric.domain.dto.Message.GpsCoordinate;
import by.nhorushko.crudgeneric.domain.entity.MessageEntity;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperEntityDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class AbsServiceCRUDTest {

    @Mock
    private AbsMapperEntityDto<MessageEntity, Message> mockedMapper;

    @Mock
    private JpaRepository<MessageEntity, Long> mockedRepository;

    @Captor
    private ArgumentCaptor<Message> messageArgumentCaptor;

    @Captor
    private ArgumentCaptor<MessageEntity> messageEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<Message>> messagesArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<MessageEntity>> messageEntitiesArgumentCaptor;

    private AbsServiceCRUD<Long, MessageEntity, Message, JpaRepository<MessageEntity, Long>> service;

    @Before
    public void initializeService() {
        this.service = new AbsServiceCRUD<>(this.mockedMapper, this.mockedRepository) {
        };
    }

    @Test
    public void messageShouldBeSaved() {
        final Message givenMessageDtoToBeSaved = new Message(null, new GpsCoordinate(5.5F, 6.6F),
                10, 15, 20);

        final MessageEntity givenMessageEntityToBeSaved = new MessageEntity(null, 5.5F, 6.6F,
                10, 15, 20);
        when(this.mockedMapper.revMap(any(Message.class))).thenReturn(givenMessageEntityToBeSaved);

        final MessageEntity givenSavedMessageEntity = new MessageEntity(255L, 5.5F, 6.6F, 10,
                15, 20);
        when(this.mockedRepository.save(any(MessageEntity.class))).thenReturn(givenSavedMessageEntity);

        final Message givenSavedMessageDto = new Message(255L, new GpsCoordinate(5.5F, 6.6F),
                10, 15, 20);
        when(this.mockedMapper.map(any(MessageEntity.class))).thenReturn(givenSavedMessageDto);

        final Message actual = this.service.save(givenMessageDtoToBeSaved);
        assertSame(givenSavedMessageDto, actual);

        verify(this.mockedMapper, times(1)).revMap(this.messageArgumentCaptor.capture());
        verify(this.mockedRepository, times(1)).save(this.messageEntityArgumentCaptor.capture());
        verify(this.mockedMapper, times(1)).map(this.messageEntityArgumentCaptor.capture());

        assertSame(givenMessageDtoToBeSaved, this.messageArgumentCaptor.getValue());

        final List<MessageEntity> expectedCapturedMessageEntityArguments = List.of(givenMessageEntityToBeSaved,
                givenSavedMessageEntity);
        assertEquals(expectedCapturedMessageEntityArguments, this.messageEntityArgumentCaptor.getAllValues());
    }

    @Test
    public void messagesShouldBeSaved() {
        final List<Message> givenMessagesToBeSaved = List.of(
                new Message(null, new GpsCoordinate(5.5F, 6.6F), 10, 15, 20),
                new Message(5L, new GpsCoordinate(7.7F, 8.8F), 11, 16, 21)
        );

        final List<MessageEntity> givenMessageEntitiesToBeSaved = List.of(
                new MessageEntity(null, 5.5F, 6.6F, 10, 15, 20),
                new MessageEntity(5L, 7.7F, 8.8F, 11, 16, 21)
        );
        when(this.mockedMapper.revMap(anyCollectionOf(Message.class))).thenReturn(givenMessageEntitiesToBeSaved);

        final List<MessageEntity> givenSavedMessageEntities = List.of(
                new MessageEntity(255L, 5.5F, 6.6F, 10, 15, 20),
                new MessageEntity(5L, 7.7F, 8.8F, 11, 16, 21)
        );
        when(this.mockedRepository.saveAll(anyCollectionOf(MessageEntity.class))).thenReturn(givenSavedMessageEntities);

        final List<Message> givenSavedMessages = List.of(
                new Message(255L, new GpsCoordinate(5.5F, 6.6F), 10, 15, 20),
                new Message(5L, new GpsCoordinate(7.7F, 8.8F), 11, 16, 21)
        );
        when(this.mockedMapper.map(anyCollectionOf(MessageEntity.class))).thenReturn(givenSavedMessages);

        final List<Message> actual = this.service.saveAll(givenMessagesToBeSaved);
        assertSame(givenSavedMessages, actual);

        verify(this.mockedMapper, times(1)).revMap(this.messagesArgumentCaptor.capture());
        verify(this.mockedRepository, times(1))
                .saveAll(this.messageEntitiesArgumentCaptor.capture());
        verify(this.mockedMapper, times(1)).map(this.messageEntitiesArgumentCaptor.capture());

        assertSame(givenMessagesToBeSaved, this.messagesArgumentCaptor.getValue());

        final List<List<MessageEntity>> expectedMessageEntityListCapturedArguments = List.of(
                givenMessageEntitiesToBeSaved, givenSavedMessageEntities);
        assertEquals(expectedMessageEntityListCapturedArguments, this.messageEntitiesArgumentCaptor.getAllValues());
    }
}
