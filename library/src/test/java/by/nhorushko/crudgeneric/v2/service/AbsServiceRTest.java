package by.nhorushko.crudgeneric.v2.service;

import by.nhorushko.crudgeneric.domain.dto.Message;
import by.nhorushko.crudgeneric.domain.dto.Message.GpsCoordinate;
import by.nhorushko.crudgeneric.domain.entity.MessageEntity;
import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgeneric.v2.mapper.AbsMapperDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class AbsServiceRTest {

    @Mock
    private AbsMapperDto<MessageEntity, Message> mockedMapper;

    @Mock
    private JpaRepository<MessageEntity, Long> mockedRepository;

    @Captor
    private ArgumentCaptor<Long> longArgumentCaptor;

    @Captor
    private ArgumentCaptor<MessageEntity> messageEntityArgumentCaptor;

    @Captor
    private ArgumentCaptor<Collection<Long>> longsArgumentCaptor;

    @Captor
    private ArgumentCaptor<List<MessageEntity>> messageEntitiesArgumentCaptor;

    private MessageReadServiceR service;

    @Before
    public void initializeService() {
        this.service = new MessageReadServiceR(this.mockedMapper, this.mockedRepository);
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

        final Optional<Message> optionalActual = this.service.getByIdOptional(givenId);
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

        final Optional<Message> optionalActual = this.service.getByIdOptional(givenId);
        assertTrue(optionalActual.isEmpty());

        verify(this.mockedRepository, times(1)).findById(this.longArgumentCaptor.capture());
        verify(this.mockedMapper, times(0)).toDto(any(MessageEntity.class));

        assertSame(givenId, this.longArgumentCaptor.getValue());
    }

    @Test
    public void messagesShouldBeFoundBeIds() {
        final List<Long> givenIds = List.of(255L, 256L);

        final List<MessageEntity> givenMessageEntities = List.of(
                new MessageEntity(255L, 3.3F, 4.4F, 10, 11, 12),
                new MessageEntity(256L, 5.5F, 6.6F, 13, 14, 15));
        when(this.mockedRepository.findAllById(anyCollectionOf(Long.class))).thenReturn(givenMessageEntities);

        final List<Message> givenMessages = List.of(
                new Message(255L, new GpsCoordinate(3.3F, 4.4F), 10, 11, 12),
                new Message(256L, new GpsCoordinate(5.5F, 6.6F), 13, 14, 15)
        );
        when(this.mockedMapper.toDtos(anyCollectionOf(MessageEntity.class))).thenReturn(givenMessages);

        final List<Message> actual = this.service.getById(givenIds);
        assertSame(givenMessages, actual);

        verify(this.mockedRepository, times(1)).findAllById(this.longsArgumentCaptor.capture());
        verify(this.mockedMapper, times(1)).toDtos(this.messageEntitiesArgumentCaptor.capture());

        assertSame(givenIds, this.longsArgumentCaptor.getValue());
        assertSame(givenMessageEntities, this.messageEntitiesArgumentCaptor.getValue());
    }

    @Test
    public void messageShouldBeGotById() {
        final Long givenId = 255L;
        final MessageEntity foundEntity = new MessageEntity(givenId, 5.5F, 4.6F, 10, 15,
                20);
        when(this.mockedRepository.findById(anyLong())).thenReturn(Optional.of(foundEntity));

        final Message expected = new Message(givenId, new GpsCoordinate(5.5F, 4.6F), 10, 15,
                20);
        when(this.mockedMapper.toDto(any(MessageEntity.class))).thenReturn(expected);

        final Message actual = this.service.getById(givenId);
        assertSame(expected, actual);

        verify(this.mockedRepository, times(1)).findById(this.longArgumentCaptor.capture());
        verify(this.mockedMapper, times(1)).toDto(this.messageEntityArgumentCaptor.capture());

        assertSame(givenId, this.longArgumentCaptor.getValue());
        assertSame(foundEntity, this.messageEntityArgumentCaptor.getValue());
    }

    @Test(expected = AppNotFoundException.class)
    public void messageShouldNotBeGotById() {
        final Long givenId = 255L;
        when(this.mockedRepository.findById(anyLong())).thenReturn(empty());
        this.service.getById(givenId);
    }

    @Test
    public void messageShouldExist() {
        when(this.mockedRepository.existsById(anyLong())).thenReturn(true);
        assertTrue(this.service.isExist(255L));
    }

    @Test
    public void messageShouldNotExist() {
        when(this.mockedRepository.existsById(anyLong())).thenReturn(false);
        assertFalse(this.service.isExist(255L));
    }

    @SuppressWarnings("all")
    private static final class MessageReadServiceR
            extends AbsServiceR<Long, MessageEntity, Message, AbsMapperDto<MessageEntity, Message>, JpaRepository<MessageEntity, Long>> {

        public MessageReadServiceR(AbsMapperDto<MessageEntity, Message> mapper,
                                   JpaRepository<MessageEntity, Long> repository) {
            super(mapper, repository);
        }
    }
}
