package Repository;

import Entity.ProductGroupEntity;
import Utils.FileHandler;
import Utils.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductGroupJSONRepositoryTest {

    @InjectMocks
    private ProductGroupJSONRepository repository;

    @Mock
    private FileHandler fileHandler;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ObjectWriter objectWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        repository.file = fileHandler;
        repository.mapper = objectMapper;


    }

    @Test
    void testSave_SuccessfulSave() throws JsonProcessingException {
        when(fileHandler.createFileIfNotExist()).thenReturn(new Result(true, null));
        when(fileHandler.readJSONStringFromFile()).thenReturn(new Result(true, "SomeJsonData"));
        when(fileHandler.writeJSONStringInFile(anyString())).thenReturn(new Result(true, null));

        when(objectMapper.readValue(anyString(), eq(ProductGroupEntity[].class))).thenReturn(new ProductGroupEntity[0]);
        when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
        when(objectWriter.writeValueAsString(any())).thenReturn("SomeJsonData");


        Result result = repository.save(new ProductGroupEntity("testName", "Test Group"));

        assertTrue(result.isSuccess());
        verify(fileHandler, times(1)).writeJSONStringInFile("SomeJsonData");
    }

    @Test
    void testSave_UnSuccessfulSave() throws JsonProcessingException {
        when(fileHandler.createFileIfNotExist()).thenReturn(new Result(true, null));
        when(fileHandler.readJSONStringFromFile()).thenReturn(new Result(false, "Some problem with reading file"));
        when(fileHandler.writeJSONStringInFile(anyString())).thenReturn(new Result(true, null));

        when(objectMapper.readValue(anyString(), eq(ProductGroupEntity[].class))).thenReturn(new ProductGroupEntity[0]);
        when(objectMapper.writerWithDefaultPrettyPrinter()).thenReturn(objectWriter);
        when(objectWriter.writeValueAsString(any())).thenReturn("SomeJsonData");


        Result result = repository.save(new ProductGroupEntity("testName", "Test Group"));

        assertFalse(result.isSuccess());
        assertEquals("Some problem with reading file", result.getMessage());
        verify(fileHandler, times(0)).writeJSONStringInFile("SomeJsonData");
    }
}
